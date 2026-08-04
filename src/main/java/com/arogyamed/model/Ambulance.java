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

    // Unique Ambulance Number
    @Column(nullable = false, unique = true)
    private String ambulanceNumber;

    // Driver Details
    private String driverName;

    @Column(nullable = false, unique = true)
    private String driverPhone;

    // Current Location
    // (Future: Live GPS Latitude & Longitude)
    private String currentLocation;

    // Ambulance Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmbulanceStatus status = AmbulanceStatus.AVAILABLE;

    // Availability
    private boolean available = true;

    // * Vehicle Verification

    // Registration Number (RC Number)
    @Column(nullable = false, unique = true)
    private String registrationNumber;

    // Registration Certificate File Path / URL
    private String registrationCertificate;

    // Insurance Document File Path / URL
    private String insuranceDocument;

    // Admin Verification
    private Boolean verified = false;

    //* Future Enhancements

    // Insurance Expiry Date
    private LocalDate insuranceExpiryDate;

    // Fitness Certificate Expiry Date
    private LocalDate fitnessCertificateExpiryDate;

    // Pollution Certificate Expiry Date
    private LocalDate pollutionExpiryDate;

}