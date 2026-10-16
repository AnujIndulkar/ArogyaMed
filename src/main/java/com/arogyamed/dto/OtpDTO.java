package com.arogyamed.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class OtpDTO {

    private OtpDTO() {
    }

    public record SendRequest(
            @NotBlank(message = "Email is required")
            @Email(message = "Enter a valid email address")
            @Size(max = 190, message = "Email is too long")
            String email) {
    }

    public record VerifyRequest(
            @NotBlank(message = "Email is required")
            @Email(message = "Enter a valid email address")
            @Size(max = 190, message = "Email is too long")
            String email,

            @NotBlank(message = "Enter the 6-digit code")
            @Pattern(regexp = "\\d{6}", message = "The code is 6 digits")
            String otp) {
    }

    // tells the page how long to show the "Resend in 60s" countdown
    public record SendResponse(String message, int resendAfterSeconds, int expiresInSeconds) {
    }

    // the proof the registration form sends along with the other details
    public record VerifyResponse(String verificationToken, int expiresInSeconds) {
    }
}