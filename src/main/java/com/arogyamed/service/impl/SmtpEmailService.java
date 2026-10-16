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

@Service
public class SmtpEmailService implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);

    private final JavaMailSender mailSender;
    private final String from;

    public SmtpEmailService(JavaMailSender mailSender, @Value("${app.mail.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @Override
    public void sendOtp(String to, String code, int validMinutes) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ArogyaMed <" + from + ">");
        message.setTo(to);
        message.setSubject("Your ArogyaMed verification code");
        message.setText(
                "Your ArogyaMed verification code is: " + code + "\n\n"
                        + "It is valid for " + validMinutes + " minutes. Do not share it with anyone.\n\n"
                        + "If you did not try to register, you can ignore this email.");

        try {
            mailSender.send(message);
        } catch (MailException e) {
            log.error("Could not send OTP email", e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "We couldn't send the email. Please try again in a moment");
        }
    }
}