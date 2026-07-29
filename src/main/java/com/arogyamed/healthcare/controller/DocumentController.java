package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.DocumentRequestDTO;
import com.arogyamed.healthcare.dto.DocumentResponseDTO;
import com.arogyamed.healthcare.model.DocumentModule;
import com.arogyamed.healthcare.model.DocumentType;
import com.arogyamed.healthcare.model.Role;
import com.arogyamed.healthcare.model.VerificationStatus;
import com.arogyamed.healthcare.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // ==========================================================
    // CORE CRUD
    // ==========================================================

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @ModelAttribute DocumentRequestDTO requestDTO) {

        return ResponseEntity.ok(documentService.uploadDocument(file, requestDTO));

    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable Long documentId) {

        return ResponseEntity.ok(documentService.getDocumentById(documentId));
    }

    @GetMapping("/module/{documentModule}/{referenceId}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByModule(
            @PathVariable DocumentModule documentModule,
            @PathVariable Long referenceId) {

        return ResponseEntity.ok(documentService.getDocumentsByModule(documentModule, referenceId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByUser(@PathVariable Long userId) {

        return ResponseEntity.ok(documentService.getDocumentsByUser(userId));
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {

        Resource resource = documentService.downloadDocument(documentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<String> deleteDocument(
            @PathVariable Long documentId) {

        documentService.deleteDocument(documentId);

        return ResponseEntity.ok("Document deleted successfully.");
    }

    // ==========================================================
    // VERIFICATION ACTIONS
    // ==========================================================

    @PutMapping("/{documentId}/verify")
    public ResponseEntity<DocumentResponseDTO> verifyDocument(
            @PathVariable Long documentId,
            @RequestParam Long verifiedByUserId) {

        return ResponseEntity.ok(documentService.verifyDocument(documentId, verifiedByUserId));
    }

    @PutMapping("/{documentId}/reject")
    public ResponseEntity<DocumentResponseDTO> rejectDocument(
            @PathVariable Long documentId,
            @RequestParam Long verifiedByUserId,
            @RequestParam String rejectionReason) {

        return ResponseEntity.ok(documentService.rejectDocument(documentId, verifiedByUserId, rejectionReason));
    }

    // ==========================================================
    // SEARCH BY USER
    // ==========================================================

    @GetMapping("/search/user-id")
    public List<DocumentResponseDTO> searchByUserId(@RequestParam Long userId) {
        return documentService.searchByUserId(userId);
    }

    @GetMapping("/search/user-name")
    public List<DocumentResponseDTO> searchByUserName(@RequestParam String fullName) {
        return documentService.searchByUserName(fullName);
    }

    // ==========================================================
    // SEARCH BY ROLE
    // ==========================================================

    @GetMapping("/search/role")
    public List<DocumentResponseDTO> searchByRole(@RequestParam Role role) {
        return documentService.searchByRole(role);
    }

    // ==========================================================
    // SEARCH BY DOCUMENT TYPE
    // ==========================================================

    @GetMapping("/search/document-type")
    public List<DocumentResponseDTO> searchByDocumentType(@RequestParam DocumentType documentType) {
        return documentService.searchByDocumentType(documentType);
    }

    @GetMapping("/search/document-type/aadhaar")
    public List<DocumentResponseDTO> searchAadhaar() {
        return documentService.searchByDocumentType(DocumentType.AADHAAR);
    }

    @GetMapping("/search/document-type/pan")
    public List<DocumentResponseDTO> searchPan() {
        return documentService.searchByDocumentType(DocumentType.PAN);
    }

    @GetMapping("/search/document-type/driving-license")
    public List<DocumentResponseDTO> searchDrivingLicense() {
        return documentService.searchByDocumentType(DocumentType.DRIVING_LICENSE);
    }

    @GetMapping("/search/document-type/medical-license")
    public List<DocumentResponseDTO> searchMedicalLicense() {
        return documentService.searchByDocumentType(DocumentType.MEDICAL_LICENSE);
    }

    @GetMapping("/search/document-type/gst")
    public List<DocumentResponseDTO> searchGst() {
        return documentService.searchByDocumentType(DocumentType.GST);
    }

    @GetMapping("/search/document-type/company-registration")
    public List<DocumentResponseDTO> searchCompanyRegistration() {
        return documentService.searchByDocumentType(DocumentType.COMPANY_REGISTRATION);
    }

    @GetMapping("/search/document-number")
    public List<DocumentResponseDTO> searchByDocumentNumber(@RequestParam String documentNumber) {
        return documentService.searchByDocumentNumber(documentNumber);
    }

    // ==========================================================
    // SEARCH BY VERIFICATION STATUS
    // ==========================================================

    @GetMapping("/search/verification-status")
    public List<DocumentResponseDTO> searchByVerificationStatus(@RequestParam VerificationStatus verificationStatus) {
        return documentService.searchByVerificationStatus(verificationStatus);
    }

    @GetMapping("/search/verified")
    public List<DocumentResponseDTO> searchVerified() {
        return documentService.searchVerifiedDocuments();
    }

    @GetMapping("/search/pending")
    public List<DocumentResponseDTO> searchPending() {
        return documentService.searchPendingDocuments();
    }

    @GetMapping("/search/rejected")
    public List<DocumentResponseDTO> searchRejected() {
        return documentService.searchRejectedDocuments();
    }

    @GetMapping("/search/verified-by")
    public List<DocumentResponseDTO> searchByVerifiedById(@RequestParam Long verifiedByUserId) {
        return documentService.searchByVerifiedById(verifiedByUserId);
    }

    @GetMapping("/search/verified-by-name")
    public List<DocumentResponseDTO> searchByVerifiedByName(@RequestParam String verifiedByName) {
        return documentService.searchByVerifiedByName(verifiedByName);
    }

    // ==========================================================
    // SEARCH BY DATE
    // ==========================================================

    @GetMapping("/search/upload-date-range")
    public List<DocumentResponseDTO> searchByUploadDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return documentService.searchByUploadDateRange(startDate, endDate);
    }

    @GetMapping("/search/expiry-date-range")
    public List<DocumentResponseDTO> searchByExpiryDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return documentService.searchByExpiryDateRange(startDate, endDate);
    }

    @GetMapping("/search/expired")
    public List<DocumentResponseDTO> searchExpired() {
        return documentService.searchExpiredDocuments();
    }

    @GetMapping("/search/expiring-soon")
    public List<DocumentResponseDTO> searchExpiringSoon(@RequestParam(defaultValue = "30") int days) {
        return documentService.searchExpiringSoon(days);
    }

    // ==========================================================
    // SEARCH BY FILE TYPE / SIZE
    // ==========================================================

    @GetMapping("/search/file-type")
    public List<DocumentResponseDTO> searchByFileType(@RequestParam String fileType) {
        return documentService.searchByFileType(fileType);
    }

    @GetMapping("/search/file-size-min")
    public List<DocumentResponseDTO> searchByFileSizeMin(@RequestParam Long minSize) {
        return documentService.searchByFileSizeMin(minSize);
    }

    @GetMapping("/search/file-size-max")
    public List<DocumentResponseDTO> searchByFileSizeMax(@RequestParam Long maxSize) {
        return documentService.searchByFileSizeMax(maxSize);
    }

    @GetMapping("/search/file-size-range")
    public List<DocumentResponseDTO> searchByFileSizeRange(
            @RequestParam Long minSize,
            @RequestParam Long maxSize) {
        return documentService.searchByFileSizeRange(minSize, maxSize);
    }

    // ==========================================================
    // COMBINED FILTERS
    // ==========================================================

    @GetMapping("/search/role-and-type")
    public List<DocumentResponseDTO> searchByRoleAndDocumentType(
            @RequestParam Role role,
            @RequestParam DocumentType documentType) {
        return documentService.searchByRoleAndDocumentType(role, documentType);
    }

    @GetMapping("/search/role-and-status")
    public List<DocumentResponseDTO> searchByRoleAndVerificationStatus(
            @RequestParam Role role,
            @RequestParam VerificationStatus verificationStatus) {
        return documentService.searchByRoleAndVerificationStatus(role, verificationStatus);
    }

    @GetMapping("/search/type-and-status")
    public List<DocumentResponseDTO> searchByDocumentTypeAndVerificationStatus(
            @RequestParam DocumentType documentType,
            @RequestParam VerificationStatus verificationStatus) {
        return documentService.searchByDocumentTypeAndVerificationStatus(documentType, verificationStatus);
    }

    @GetMapping("/search/combined")
    public List<DocumentResponseDTO> searchDocuments(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) DocumentType documentType,
            @RequestParam(required = false) VerificationStatus verificationStatus,
            @RequestParam(required = false) String fileType,
            @RequestParam(required = false) Long uploadedById,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Boolean active) {

        return documentService.searchDocuments(
                role, documentType, verificationStatus, fileType,
                uploadedById, startDate, endDate, active);
    }
}