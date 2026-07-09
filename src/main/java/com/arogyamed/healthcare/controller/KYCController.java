package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.KYCRequestDTO;
import com.arogyamed.healthcare.dto.KYCResponseDTO;
import com.arogyamed.healthcare.service.KYCService;
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

}
