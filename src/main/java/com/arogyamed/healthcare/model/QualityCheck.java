package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "quality_checks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
      Medicine being inspected
     */
    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    /**
      Company that manufactured the medicine
     */
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    /**
      Inspector/Admin who performed inspection
     */
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin inspector;

    /**
      Batch Number
     */
    @Column(nullable = false)
    private String batchNumber;

    /**
      Packaging Verification
     */
    private boolean packagingVerified;

    /**
     * Seal Verification
     */
    private boolean sealVerified;

    /**
      Temperature Verification
     */
    private boolean temperatureVerified;

    /**
      Expiry Verification
     */
    private boolean expiryVerified;

    /**
      Additional inspection remarks
     */
    @Column(length = 1000)
    private String inspectorRemarks;

    /**
      Inspection Date
     */
    private LocalDate inspectionDate;

    /**
      Current Quality Status
     */
    @Enumerated(EnumType.STRING)
    private QualityStatus qualityStatus;

}