package com.arogyamed.service;

public interface EmailService {

    void sendOtp(String to, String code, int validMinutes);
}
