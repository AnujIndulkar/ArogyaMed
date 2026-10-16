package com.arogyamed.service;

import com.arogyamed.dto.OtpDTO;

public interface EmailVerificationService {

    // Step 1: email a new code
    OtpDTO.SendResponse sendOtp(String email);

    // Step 2: check the code, hand back the proof
    OtpDTO.VerifyResponse verifyOtp(String email, String otp);

    // Used by registration: throws 403 unless this email was verified with this proof
    void assertVerified(String email, String verificationToken);

    // Used by registration after the account is saved
    void clear(String email);
}