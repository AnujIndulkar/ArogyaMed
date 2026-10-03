package com.arogyamed.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ambulances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Linked user account (AMBULANCE_PROVIDER role) that logs in and manages this ambulance
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String ambulanceNumber;

    private String driverName;

    @Column(nullable = false, unique = true)
    private String driverPhone;

    private String currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmbulanceStatus status = AmbulanceStatus.AVAILABLE;

    private boolean available = true;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    private String registrationCertificate;

    private String insuranceDocument;

    private Boolean verified = false;

    private LocalDate insuranceExpiryDate;

    private LocalDate fitnessCertificateExpiryDate;

    private LocalDate pollutionExpiryDate;

}