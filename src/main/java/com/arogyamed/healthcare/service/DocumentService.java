package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.DocumentRequestDTO;
import com.arogyamed.healthcare.dto.DocumentResponseDTO;
import com.arogyamed.healthcare.model.DocumentModule;
import com.arogyamed.healthcare.model.DocumentType;
import com.arogyamed.healthcare.model.Role;
import com.arogyamed.healthcare.model.VerificationStatus;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DocumentService {

    DocumentResponseDTO uploadDocument(MultipartFile file, DocumentRequestDTO requestDTO);

    DocumentResponseDTO getDocumentById(Long documentId);

    List<DocumentResponseDTO> getDocumentsByModule(DocumentModule documentModule, Long referenceId);

    List<DocumentResponseDTO> getDocumentsByUser(Long userId);

    Resource downloadDocument(Long documentId);

    void deleteDocument(Long documentId);

    // ================= Verification Actions =================

    DocumentResponseDTO verifyDocument(Long documentId, Long verifiedByUserId);

    DocumentResponseDTO rejectDocument(Long documentId, Long verifiedByUserId, String rejectionReason);

    // ================= Enterprise Search & Filtering =================

    List<DocumentResponseDTO> searchByUserId(Long userId);

    List<DocumentResponseDTO> searchByUserName(String fullName);

    List<DocumentResponseDTO> searchByRole(Role role);

    List<DocumentResponseDTO> searchByDocumentType(DocumentType documentType);

    List<DocumentResponseDTO> searchByDocumentNumber(String documentNumber);

    List<DocumentResponseDTO> searchByVerificationStatus(VerificationStatus verificationStatus);

    List<DocumentResponseDTO> searchVerifiedDocuments();

    List<DocumentResponseDTO> searchPendingDocuments();

    List<DocumentResponseDTO> searchRejectedDocuments();

    List<DocumentResponseDTO> searchByVerifiedById(Long verifiedByUserId);

    List<DocumentResponseDTO> searchByVerifiedByName(String verifiedByName);

    List<DocumentResponseDTO> searchByUploadDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<DocumentResponseDTO> searchByExpiryDateRange(LocalDate startDate, LocalDate endDate);

    List<DocumentResponseDTO> searchExpiredDocuments();

    List<DocumentResponseDTO> searchExpiringSoon(int days);

    List<DocumentResponseDTO> searchByFileType(String fileType);

    List<DocumentResponseDTO> searchByFileSizeMin(Long minSize);

    List<DocumentResponseDTO> searchByFileSizeMax(Long maxSize);

    List<DocumentResponseDTO> searchByFileSizeRange(Long minSize, Long maxSize);

    List<DocumentResponseDTO> searchByRoleAndDocumentType(Role role, DocumentType documentType);

    List<DocumentResponseDTO> searchByRoleAndVerificationStatus(Role role, VerificationStatus verificationStatus);

    List<DocumentResponseDTO> searchByDocumentTypeAndVerificationStatus(DocumentType documentType, VerificationStatus verificationStatus);

    List<DocumentResponseDTO> searchDocuments(
            Role role,
            DocumentType documentType,
            VerificationStatus verificationStatus,
            String fileType,
            Long uploadedById,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean active
    );
}
