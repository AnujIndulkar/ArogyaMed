package com.arogyamed.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "stored_file_name", nullable = false, unique = true)
    private String storedFileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_extension", nullable = false)
    private String fileExtension;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_module", nullable = false)
    private DocumentModule documentModule;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Builder.Default
    @Column(name = "active")
    private Boolean active = true;

    @Builder.Default
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt = LocalDateTime.now();

    // ==========================================================
    // MODULE 30 - DOCUMENT MANAGEMENT SEARCH & FILTERING FIELDS
    // ==========================================================

    // Type of document content (Aadhaar, PAN, GST, Medical License, etc.)
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    // Actual ID / License / Registration number printed on the document
    @Column(name = "document_number")
    private String documentNumber;

    // Role of the user who uploaded the document (denormalized for fast filtering)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by")
    private User verifiedBy;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "rejection_reason", length = 1000)
    private String rejectionReason;
}