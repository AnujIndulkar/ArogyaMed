package com.arogyamed.model;

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

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "inspector_id")
    private QualityInspector inspector;

    @Column(nullable = false)
    private String batchNumber;

    private boolean packagingVerified;

    private boolean sealVerified;

    private boolean temperatureVerified;

    private boolean expiryVerified;

    @Column(length = 1000)
    private String inspectorRemarks;

    private LocalDate inspectionDate;

    @Enumerated(EnumType.STRING)
    private QualityStatus qualityStatus;

}