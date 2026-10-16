package com.arogyamed.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// One row per email address: the current code, and (after success) the proof of verification
@Entity
@Table(name = "email_otps", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
public class EmailOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 190)
    private String email;

    // the code is never stored in plain text, only a keyed hash of it
    @Column(length = 64)
    private String otpHash;
    private LocalDateTime expiresAt;
    private int attempts;

    // resend limits: one code per minute, 5 codes per hour
    private LocalDateTime lastSentAt;
    private LocalDateTime windowStart;
    private int sentInWindow;

    // after the right code is entered, registration needs this proof
    @Column(length = 64)
    private String proofHash;
    private LocalDateTime proofExpiresAt;
}
