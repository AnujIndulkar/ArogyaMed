package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.KYCRequestDTO;
import com.arogyamed.healthcare.dto.KYCResponseDTO;
import com.arogyamed.healthcare.service.KYCService;
import com.arogyamed.healthcare.model.KYCStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kyc")
public class KYCController {

    @Autowired
    private KYCService kycService;

    // Submit KYC
    @PostMapping
    public KYCResponseDTO submitKYC(@RequestBody KYCRequestDTO request) {
        return kycService.submitKYC(request);
    }

    // Get KYC by ID
    @GetMapping("/{id}")
    public KYCResponseDTO getKYCById(@PathVariable Long id) {
        return kycService.getKYCById(id);
    }

    // Get KYC by User ID
    @GetMapping("/user/{userId}")
    public KYCResponseDTO getKYCByUser(@PathVariable Long userId) {
        return kycService.getKYCByUser(userId);
    }

    // Get All KYC
    @GetMapping
    public List<KYCResponseDTO> getAllKYC() {
        return kycService.getAllKYC();
    }

    // Approve KYC
    @PutMapping("/{id}/approve")
    public KYCResponseDTO approveKYC(@PathVariable Long id) {
        return kycService.approveKYC(id);
    }

    // Reject KYC
    @PutMapping("/{id}/reject")
    public KYCResponseDTO rejectKYC(@PathVariable Long id, @RequestParam String remarks) {
        return kycService.rejectKYC(id, remarks);
    }

    // ================= Search =================

    // Search by Full Name
    @GetMapping("/search/full-name")
    public List<KYCResponseDTO> searchByFullName(@RequestParam String fullName) {
        return kycService.searchByFullName(fullName);
    }

    // Search by Email
    @GetMapping("/search/email")
    public List<KYCResponseDTO> searchByEmail(@RequestParam String email) {
        return kycService.searchByEmail(email);
    }

    // Search by Document Type
    @GetMapping("/search/document-type")
    public List<KYCResponseDTO> searchByDocumentType(@RequestParam String documentType) {
        return kycService.searchByDocumentType(documentType);
    }

    // Search by Document Number
    @GetMapping("/search/document-number")
    public List<KYCResponseDTO> searchByDocumentNumber(@RequestParam String documentNumber) {
        return kycService.searchByDocumentNumber(documentNumber);
    }

    // Search by Status
    @GetMapping("/search/status")
    public List<KYCResponseDTO> searchByStatus(@RequestParam KYCStatus status) {
        return kycService.searchByStatus(status);
    }

    // Search by Remarks
    @GetMapping("/search/remarks")
    public List<KYCResponseDTO> searchByRemarks(@RequestParam String remarks) {
        return kycService.searchByRemarks(remarks);
    }

}
