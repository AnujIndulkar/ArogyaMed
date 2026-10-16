package com.arogyamed.service.impl;

import com.arogyamed.dto.OtpDTO;
import com.arogyamed.model.EmailOtp;
import com.arogyamed.repository.EmailOtpRepository;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.service.EmailService;
import com.arogyamed.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Locale;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private static final int OTP_MINUTES = 5;          // how long a code works
    private static final int PROOF_MINUTES = 15;       // how long to finish the registration form
    private static final int RESEND_SECONDS = 60;      // wait between codes
    private static final int MAX_ATTEMPTS = 5;         // wrong guesses per code
    private static final int MAX_SENDS_PER_HOUR = 5;   // codes per email per hour

    private final EmailOtpRepository repository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final SecretKeySpec key;
    private final SecureRandom random = new SecureRandom();

    public EmailVerificationServiceImpl(EmailOtpRepository repository,
                                        EmailService emailService,
                                        UserRepository userRepository,
                                        @Value("${app.otp.secret}") String secret) {
        if (secret == null || secret.length() < 16) {
            throw new IllegalStateException("app.otp.secret must be set (at least 16 characters)");
        }
        this.repository = repository;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    // ============ 1. Send a code ============

    @Override
    @Transactional
    public OtpDTO.SendResponse sendOtp(String rawEmail) {

        String email = normalize(rawEmail);
        LocalDateTime now = LocalDateTime.now();

        // no point verifying an email that already has an account
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This email is already registered. Please sign in instead");
        }

        EmailOtp row = repository.findByEmail(email).orElseGet(() -> {
            EmailOtp created = new EmailOtp();
            created.setEmail(email);
            return created;
        });

        // one code per minute
        if (row.getLastSentAt() != null) {
            long since = Duration.between(row.getLastSentAt(), now).getSeconds();
            if (since < RESEND_SECONDS) {
                throw tooMany("Please wait " + (RESEND_SECONDS - since) + " seconds before asking for a new code");
            }
        }

        // at most 5 codes per hour, so nobody can use us to spam an inbox
        if (row.getWindowStart() == null || now.isAfter(row.getWindowStart().plusHours(1))) {
            row.setWindowStart(now);
            row.setSentInWindow(0);
        }
        if (row.getSentInWindow() >= MAX_SENDS_PER_HOUR) {
            throw tooMany("Too many codes were requested for this email. Please try again in an hour");
        }

        String code = String.format("%06d", random.nextInt(1_000_000));

        row.setOtpHash(hash(email, code));
        row.setExpiresAt(now.plusMinutes(OTP_MINUTES));
        row.setAttempts(0);
        row.setProofHash(null);          // a new code cancels any older proof
        row.setProofExpiresAt(null);
        row.setLastSentAt(now);
        row.setSentInWindow(row.getSentInWindow() + 1);
        repository.save(row);

        // if the email cannot be sent this throws and everything above is rolled back
        emailService.sendOtp(email, code, OTP_MINUTES);

        return new OtpDTO.SendResponse(
                "We sent a 6-digit code to " + email,
                RESEND_SECONDS,
                OTP_MINUTES * 60);
    }

    // ============ 2. Check the code ============

    // noRollbackFor: the wrong-attempt counter must be saved even though we then throw an error
    @Override
    @Transactional(noRollbackFor = ResponseStatusException.class)
    public OtpDTO.VerifyResponse verifyOtp(String rawEmail, String otp) {

        String email = normalize(rawEmail);
        LocalDateTime now = LocalDateTime.now();

        EmailOtp row = repository.findByEmail(email)
                .orElseThrow(() -> bad("Please ask for a code first"));

        if (row.getOtpHash() == null) {
            throw bad("Please ask for a new code");
        }
        if (row.getExpiresAt() == null || now.isAfter(row.getExpiresAt())) {
            throw bad("This code has expired. Please ask for a new one");
        }
        if (row.getAttempts() >= MAX_ATTEMPTS) {
            throw tooMany("Too many wrong attempts. Please ask for a new code");
        }

        if (!safeEquals(row.getOtpHash(), hash(email, otp))) {
            row.setAttempts(row.getAttempts() + 1);
            repository.save(row);
            int left = MAX_ATTEMPTS - row.getAttempts();
            throw bad(left > 0
                    ? "Wrong code. " + left + " attempt(s) left"
                    : "Too many wrong attempts. Please ask for a new code");
        }

        // Correct. The code is now used up, and we hand back a one-time proof.
        byte[] raw = new byte[32];
        random.nextBytes(raw);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(raw);

        row.setOtpHash(null);
        row.setExpiresAt(null);
        row.setAttempts(0);
        row.setProofHash(hash(email, token));
        row.setProofExpiresAt(now.plusMinutes(PROOF_MINUTES));
        repository.save(row);

        return new OtpDTO.VerifyResponse(token, PROOF_MINUTES * 60);
    }

    // ============ 3. Used by registration ============

    @Override
    @Transactional(readOnly = true)
    public void assertVerified(String rawEmail, String token) {

        String email = normalize(rawEmail);

        if (token == null || token.isBlank()) {
            throw forbidden("Please verify your email first");
        }

        EmailOtp row = repository.findByEmail(email)
                .orElseThrow(() -> forbidden("Please verify your email first"));

        boolean valid = row.getProofHash() != null
                && row.getProofExpiresAt() != null
                && LocalDateTime.now().isBefore(row.getProofExpiresAt())
                && safeEquals(row.getProofHash(), hash(email, token));

        if (!valid) {
            throw forbidden("Your email verification is missing or has expired. Please verify again");
        }
    }

    @Override
    @Transactional
    public void clear(String rawEmail) {
        repository.findByEmail(normalize(rawEmail)).ifPresent(repository::delete);
    }

    // ============ helpers ============

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    // keyed hash (HMAC-SHA256) of "email:value", so a leaked table cannot be used to read codes
    private String hash(String email, String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            return HexFormat.of().formatHex(mac.doFinal((email + ":" + value).getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Could not hash", e);
        }
    }

    // compares without leaking, through timing, how many characters were right
    private boolean safeEquals(String a, String b) {
        return MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }

    private ResponseStatusException bad(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseStatusException tooMany(String message) {
        return new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, message);
    }

    private ResponseStatusException forbidden(String message) {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, message);
    }
}