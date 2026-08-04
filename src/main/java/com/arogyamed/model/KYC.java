package com.arogyamed.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KYC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User whose KYC is being verified
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Example:
    // Medical License
    // Pharmacy License
    // Wholesale Drug License
    // Manufacturing License
    // Driving License
    private String documentType;

    // License / Registration Number
    private String documentNumber;

    // File path / URL (future cloud storage)
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    private KYCStatus status;

    // Admin remarks if rejected
    @Column(length = 1000)
    private String remarks;

    private LocalDateTime submittedAt;

    private LocalDateTime verifiedAt;

}
