package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "barcode_qrcode")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeQRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false, unique = true)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BarcodeType barcodeType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus;

    private Integer totalScans;

    private LocalDateTime lastScannedAt;

    private String remarks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (totalScans == null) {
            totalScans = 0;
        }

        if (verificationStatus == null) {
            verificationStatus = VerificationStatus.PENDING;
        }

    }

}
