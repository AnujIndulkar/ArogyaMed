package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Optional — only set when the prescription comes from an in-app consultation
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String diagnosis;

    private String medicines;

    private String dosageInstructions;

    private LocalDate prescriptionDate;

    private String notes;

    // ==========================================================
    // Patient-uploaded external prescription fields
    // ==========================================================

    @Column(name = "prescription_image_url")
    private String prescriptionImageUrl;

    // Free-text doctor/clinic name when not linked to an in-app Doctor
    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "clinic_name")
    private String clinicName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PrescriptionStatus status;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = PrescriptionStatus.PENDING;
        }
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }
}
