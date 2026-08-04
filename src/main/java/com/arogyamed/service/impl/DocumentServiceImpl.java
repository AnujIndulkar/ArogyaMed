package com.arogyamed.service.impl;

import com.arogyamed.dto.DocumentRequestDTO;
import com.arogyamed.dto.DocumentResponseDTO;
import com.arogyamed.model.Document;
import com.arogyamed.model.DocumentModule;
import com.arogyamed.model.DocumentType;
import com.arogyamed.model.Role;
import com.arogyamed.model.User;
import com.arogyamed.model.VerificationStatus;
import com.arogyamed.repository.DocumentRepository;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final UserRepository userRepository;

    @Value("${document.upload.path}")
    private String uploadPath;

    @Override
    public DocumentResponseDTO uploadDocument(MultipartFile file, DocumentRequestDTO requestDTO) {

        try {

            User user = userRepository.findById(requestDTO.getUploadedBy()).orElseThrow(() ->
                    new RuntimeException("User not found with ID : " + requestDTO.getUploadedBy()));

            System.out.println("User Found : " + user.getId());

            String originalFileName = file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            System.out.println("Extension : " + extension);

            String storedFileName = UUID.randomUUID() + extension;

            System.out.println("Stored File Name : " + storedFileName);

            Path directoryPath = Paths.get(uploadPath);

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Upload directory created.");
            }

            Path targetPath = directoryPath.resolve(storedFileName);

            System.out.println("Target Path : " + targetPath);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File copied successfully.");

            Role effectiveRole = requestDTO.getRole() != null ? requestDTO.getRole() : user.getRole();

            Document document = Document.builder()
                    .fileName(originalFileName)
                    .storedFileName(storedFileName)
                    .fileType(file.getContentType())
                    .fileExtension(extension.replace(".", ""))
                    .fileSize(file.getSize())
                    .storagePath(targetPath.toString())
                    .documentModule(requestDTO.getDocumentModule())
                    .referenceId(requestDTO.getReferenceId())
                    .uploadedBy(user)
                    .uploadedAt(LocalDateTime.now())
                    .active(true)
                    .documentType(requestDTO.getDocumentType())
                    .documentNumber(requestDTO.getDocumentNumber())
                    .role(effectiveRole)
                    .verificationStatus(VerificationStatus.PENDING)
                    .expiryDate(requestDTO.getExpiryDate())
                    .build();

            System.out.println("Saving document...");

            Document savedDocument = documentRepository.save(document);

            System.out.println("Document Saved Successfully. ID = " + savedDocument.getId());

            return convertToResponseDTO(savedDocument);

        } catch (Exception e) {

            System.out.println("========== ERROR ==========");
            e.printStackTrace();

            throw new RuntimeException("Failed to upload document.", e);
        }
    }

    @Override
    public DocumentResponseDTO getDocumentById(Long documentId) {

        Document document = documentRepository.findById(documentId).orElseThrow(() ->
                new RuntimeException("Document not found with ID : " + documentId));

        return convertToResponseDTO(document);
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByModule(DocumentModule documentModule, Long referenceId) {

        return documentRepository.findByDocumentModuleAndReferenceIdAndActiveTrue(documentModule, referenceId)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByUser(Long userId) {

        return documentRepository.findByUploadedByIdAndActiveTrue(userId)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    public Resource downloadDocument(Long documentId) {

        try {
            Document document = documentRepository.findById(documentId).orElseThrow(() ->
                    new RuntimeException("Document not found with ID : " + documentId));

            Path filePath = Paths.get(document.getStoragePath());

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("Document not found.");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to download document.", e);

        }
    }

    @Override
    public void deleteDocument(Long documentId) {

        Document document = documentRepository.findById(documentId).orElseThrow(() ->
                new RuntimeException("Document not found with ID: " + documentId));

        document.setActive(false);

        documentRepository.save(document);
    }

    // ================= Verification Actions =================

    @Override
    public DocumentResponseDTO verifyDocument(Long documentId, Long verifiedByUserId) {

        Document document = documentRepository.findById(documentId).orElseThrow(() ->
                new RuntimeException("Document not found with ID : " + documentId));

        User verifier = userRepository.findById(verifiedByUserId).orElseThrow(() ->
                new RuntimeException("User not found with ID : " + verifiedByUserId));

        document.setVerificationStatus(VerificationStatus.VERIFIED);
        document.setVerifiedBy(verifier);
        document.setVerifiedAt(LocalDateTime.now());
        document.setRejectionReason(null);

        return convertToResponseDTO(documentRepository.save(document));
    }

    @Override
    public DocumentResponseDTO rejectDocument(Long documentId, Long verifiedByUserId, String rejectionReason) {

        Document document = documentRepository.findById(documentId).orElseThrow(() ->
                new RuntimeException("Document not found with ID : " + documentId));

        User verifier = userRepository.findById(verifiedByUserId).orElseThrow(() ->
                new RuntimeException("User not found with ID : " + verifiedByUserId));

        document.setVerificationStatus(VerificationStatus.REJECTED);
        document.setVerifiedBy(verifier);
        document.setVerifiedAt(LocalDateTime.now());
        document.setRejectionReason(rejectionReason);

        return convertToResponseDTO(documentRepository.save(document));
    }

    // ================= Enterprise Search & Filtering =================

    @Override
    public List<DocumentResponseDTO> searchByUserId(Long userId) {
        return documentRepository.findByUploadedBy_Id(userId)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByUserName(String fullName) {
        return documentRepository.findByUploadedBy_FullNameContainingIgnoreCase(fullName)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByRole(Role role) {
        return documentRepository.findByRole(role)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByDocumentType(DocumentType documentType) {
        return documentRepository.findByDocumentType(documentType)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByDocumentNumber(String documentNumber) {
        return documentRepository.findByDocumentNumberContainingIgnoreCase(documentNumber)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByVerificationStatus(VerificationStatus verificationStatus) {
        return documentRepository.findByVerificationStatus(verificationStatus)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchVerifiedDocuments() {
        return searchByVerificationStatus(VerificationStatus.VERIFIED);
    }

    @Override
    public List<DocumentResponseDTO> searchPendingDocuments() {
        return searchByVerificationStatus(VerificationStatus.PENDING);
    }

    @Override
    public List<DocumentResponseDTO> searchRejectedDocuments() {
        return searchByVerificationStatus(VerificationStatus.REJECTED);
    }

    @Override
    public List<DocumentResponseDTO> searchByVerifiedById(Long verifiedByUserId) {
        return documentRepository.findByVerifiedBy_Id(verifiedByUserId)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByVerifiedByName(String verifiedByName) {
        return documentRepository.findByVerifiedBy_FullNameContainingIgnoreCase(verifiedByName)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByUploadDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return documentRepository.findByUploadedAtBetween(startDate, endDate)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByExpiryDateRange(LocalDate startDate, LocalDate endDate) {
        return documentRepository.findByExpiryDateBetween(startDate, endDate)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchExpiredDocuments() {
        return documentRepository.findByExpiryDateBefore(LocalDate.now())
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchExpiringSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate upperBound = today.plusDays(days);
        return documentRepository.findByExpiryDateBetween(today, upperBound)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByFileType(String fileType) {
        return documentRepository.findByFileTypeContainingIgnoreCase(fileType)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByFileSizeMin(Long minSize) {
        return documentRepository.findByFileSizeGreaterThanEqual(minSize)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByFileSizeMax(Long maxSize) {
        return documentRepository.findByFileSizeLessThanEqual(maxSize)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByFileSizeRange(Long minSize, Long maxSize) {
        return documentRepository.findByFileSizeBetween(minSize, maxSize)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByRoleAndDocumentType(Role role, DocumentType documentType) {
        return documentRepository.findByRoleAndDocumentType(role, documentType)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByRoleAndVerificationStatus(Role role, VerificationStatus verificationStatus) {
        return documentRepository.findByRoleAndVerificationStatus(role, verificationStatus)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchByDocumentTypeAndVerificationStatus(DocumentType documentType, VerificationStatus verificationStatus) {
        return documentRepository.findByDocumentTypeAndVerificationStatus(documentType, verificationStatus)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @Override
    public List<DocumentResponseDTO> searchDocuments(
            Role role,
            DocumentType documentType,
            VerificationStatus verificationStatus,
            String fileType,
            Long uploadedById,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean active) {

        return documentRepository.searchDocuments(
                        role, documentType, verificationStatus, fileType,
                        uploadedById, startDate, endDate, active)
                .stream().map(this::convertToResponseDTO).toList();
    }

    // ================= Helpers =================

    private DocumentResponseDTO convertToResponseDTO(Document document) {

        return DocumentResponseDTO.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .fileType(document.getFileType())
                .fileSize(document.getFileSize())
                .fileSizeText(formatFileSize(document.getFileSize()))
                .documentModule(document.getDocumentModule())
                .uploadedAt(document.getUploadedAt())
                .downloadUrl(generateDownloadUrl(document.getId()))
                .documentType(document.getDocumentType())
                .documentNumber(document.getDocumentNumber())
                .role(document.getRole())
                .uploadedById(document.getUploadedBy() != null ? document.getUploadedBy().getId() : null)
                .uploadedByName(document.getUploadedBy() != null ? document.getUploadedBy().getFullName() : null)
                .verificationStatus(document.getVerificationStatus())
                .verifiedById(document.getVerifiedBy() != null ? document.getVerifiedBy().getId() : null)
                .verifiedByName(document.getVerifiedBy() != null ? document.getVerifiedBy().getFullName() : null)
                .verifiedAt(document.getVerifiedAt())
                .expiryDate(document.getExpiryDate())
                .expired(document.getExpiryDate() != null && document.getExpiryDate().isBefore(LocalDate.now()))
                .rejectionReason(document.getRejectionReason())
                .build();
    }

    private String formatFileSize(Long bytes) {

        if (bytes == null) {
            return "0 Bytes";
        }

        double size = bytes;

        if (size < 1024) {
            return bytes + " Bytes";
        }

        size = size / 1024;

        if (size < 1024) {
            return String.format("%.2f KB", size);
        }

        size = size / 1024;

        if (size < 1024) {
            return String.format("%.2f MB", size);
        }

        size = size / 1024;

        return String.format("%.2f GB", size);
    }

    private String generateDownloadUrl(Long documentId) {

        return "/api/documents/download/" + documentId;
    }
}