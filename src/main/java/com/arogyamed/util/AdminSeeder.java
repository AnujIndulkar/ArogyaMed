package com.arogyamed.util;

import com.arogyamed.model.Role;
import com.arogyamed.model.User;
import com.arogyamed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Creates the first admin account on startup.
 * Email and password come from configuration (never from the code or from Git):
 *   app.admin.email=...
 *   app.admin.password=...
 * If either is empty, or an account with that email exists, nothing happens.
 */
@Component
@Order(1)
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            log.info("No app.admin.email / app.admin.password set. Skipping admin seed.");
            return;
        }
        if (adminPassword.length() < 8) {
            log.warn("app.admin.password is shorter than 8 characters. Admin not created.");
            return;
        }
        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin account already exists. Skipping admin seed.");
            return;
        }

        User admin = new User();
        admin.setFullName("ArogyaMed Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setPhoneNumber("0000000000");
        admin.setRole(Role.ADMIN);
        admin.setVerified(true);
        userRepository.save(admin);

        log.info("Admin account created for {}", adminEmail);
    }
}