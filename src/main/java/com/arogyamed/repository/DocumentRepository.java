package com.arogyamed.repository;

import com.arogyamed.model.Document;
import com.arogyamed.model.DocumentModule;
import com.arogyamed.model.DocumentType;
import com.arogyamed.model.Role;
import com.arogyamed.model.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByDocumentModuleAndReferenceIdAndActiveTrue(DocumentModule documentModule, Long referenceId);

    List<Document> findByUploadedByIdAndActiveTrue(Long userId);

    List<Document> findByActiveTrue();

    boolean existsByStoredFileName(String storedFileName);

    // =========================
    // USER SEARCH
    // =========================

    List<Document> findByUploadedBy_Id(Long userId);

    List<Document> findByUploadedBy_FullNameContainingIgnoreCase(String fullName);

    // =========================
    // ROLE SEARCH
    // =========================

    List<Document> findByRole(Role role);

    List<Document> findByRoleAndActiveTrue(Role role);

    // =========================
    // DOCUMENT TYPE SEARCH
    // =========================

    List<Document> findByDocumentType(DocumentType documentType);

    List<Document> findByDocumentTypeAndActiveTrue(DocumentType documentType);

    // =========================
    // DOCUMENT NUMBER SEARCH
    // =========================

    List<Document> findByDocumentNumberContainingIgnoreCase(String documentNumber);

    // =========================
    // VERIFICATION STATUS SEARCH
    // =========================

    List<Document> findByVerificationStatus(VerificationStatus verificationStatus);

    // =========================
    // VERIFIED BY SEARCH
    // =========================

    List<Document> findByVerifiedBy_Id(Long verifiedByUserId);

    List<Document> findByVerifiedBy_FullNameContainingIgnoreCase(String verifiedByName);

    // =========================
    // UPLOAD DATE SEARCH
    // =========================

    List<Document> findByUploadedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // =========================
    // EXPIRY DATE SEARCH
    // =========================

    List<Document> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);

    List<Document> findByExpiryDateBefore(LocalDate date);

    // =========================
    // FILE TYPE / FILE SIZE SEARCH
    // =========================

    List<Document> findByFileTypeContainingIgnoreCase(String fileType);

    List<Document> findByFileSizeGreaterThanEqual(Long minSize);

    List<Document> findByFileSizeLessThanEqual(Long maxSize);

    List<Document> findByFileSizeBetween(Long minSize, Long maxSize);

    // =========================
    // COMBINED / ADVANCED SEARCH
    // =========================

    List<Document> findByRoleAndDocumentType(Role role, DocumentType documentType);

    List<Document> findByRoleAndVerificationStatus(Role role, VerificationStatus verificationStatus);

    List<Document> findByDocumentTypeAndVerificationStatus(DocumentType documentType, VerificationStatus verificationStatus);

    // =========================
    // DASHBOARD COUNTS
    // =========================

    long countByVerificationStatus(VerificationStatus verificationStatus);

    long countByDocumentType(DocumentType documentType);

    long countByRole(Role role);

    // =========================
    // DYNAMIC COMBINED FILTER
    // =========================

    @Query("""
            SELECT d FROM Document d
            WHERE (:role IS NULL OR d.role = :role)
            AND (:documentType IS NULL OR d.documentType = :documentType)
            AND (:verificationStatus IS NULL OR d.verificationStatus = :verificationStatus)
            AND (:fileType IS NULL OR LOWER(d.fileType) LIKE LOWER(CONCAT('%', :fileType, '%')))
            AND (:uploadedById IS NULL OR d.uploadedBy.id = :uploadedById)
            AND (:startDate IS NULL OR d.uploadedAt >= :startDate)
            AND (:endDate IS NULL OR d.uploadedAt <= :endDate)
            AND (:active IS NULL OR d.active = :active)
            """)
    List<Document> searchDocuments(
            @Param("role") Role role,
            @Param("documentType") DocumentType documentType,
            @Param("verificationStatus") VerificationStatus verificationStatus,
            @Param("fileType") String fileType,
            @Param("uploadedById") Long uploadedById,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("active") Boolean active
    );
}
