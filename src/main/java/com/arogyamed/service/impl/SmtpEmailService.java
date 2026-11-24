package com.arogyamed.service.impl;

import com.arogyamed.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class SmtpEmailService implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);

    private final JavaMailSender mailSender;
    private final String from;
    private final String brevoApiKey;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public SmtpEmailService(
            JavaMailSender mailSender,
            @Value("${app.mail.from}") String from,
            @Value("${BREVO_API_KEY:}") String brevoApiKey) {
        this.mailSender = mailSender;
        this.from = from;
        this.brevoApiKey = brevoApiKey;
    }

    @Override
    public void sendOtp(String to, String code, int validMinutes) {
        String subject = "Your ArogyaMed verification code";
        String bodyText = "Your ArogyaMed verification code is: " + code + "\n\n"
                + "It is valid for " + validMinutes + " minutes. Do not share it with anyone.\n\n"
                + "If you did not try to register, you can ignore this email.";

        if (brevoApiKey != null && !brevoApiKey.isBlank()) {
            sendViaBrevoApi(to, subject, bodyText);
        } else {
            sendViaSmtp(to, subject, bodyText);
        }
    }

    private void sendViaBrevoApi(String to, String subject, String bodyText) {
        try {
            String escapedBody = bodyText
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");

            String jsonPayload = "{"
                    + "\"sender\":{\"name\":\"ArogyaMed\",\"email\":\"" + from + "\"},"
                    + "\"to\":[{\"email\":\"" + to + "\"}],"
                    + "\"subject\":\"" + subject + "\","
                    + "\"textContent\":\"" + escapedBody + "\""
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://brevo.com"))
                    .timeout(Duration.ofSeconds(10))
                    .header("accept", "application/json")
                    .header("api-key", brevoApiKey.trim())
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.error("Brevo API failed ({}): {}", response.statusCode(), response.body());
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                        "We couldn't send the email. Please try again in a moment");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("Could not send OTP email via Brevo API", e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "We couldn't send the email. Please try again in a moment");
        }
    }

    private void sendViaSmtp(String to, String subject, String bodyText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ArogyaMed <" + from + ">");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(bodyText);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            log.error("Could not send OTP email", e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "We couldn't send the email. Please try again in a moment");
        }
    }
}
