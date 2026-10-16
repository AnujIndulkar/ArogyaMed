package com.arogyamed.controller;

import com.arogyamed.dto.OtpDTO;
import com.arogyamed.service.EmailVerificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/otp")
public class EmailOtpController {

    private final EmailVerificationService verificationService;

    public EmailOtpController(EmailVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    // Send (or resend) a code
    @PostMapping("/send")
    public OtpDTO.SendResponse send(@Valid @RequestBody OtpDTO.SendRequest request) {
        return verificationService.sendOtp(request.email());
    }

    // Check the code and get the proof for the registration form
    @PostMapping("/verify")
    public OtpDTO.VerifyResponse verify(@Valid @RequestBody OtpDTO.VerifyRequest request) {
        return verificationService.verifyOtp(request.email(), request.otp());
    }
}
