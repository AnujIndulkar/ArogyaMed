package com.arogyamed.controller;

import com.arogyamed.dto.QualityInspectorRequestDTO;
import com.arogyamed.dto.QualityInspectorResponseDTO;
import com.arogyamed.service.QualityInspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quality-inspectors")
public class QualityInspectorController {

    @Autowired
    private QualityInspectorService qualityInspectorService;

    @PostMapping
    public QualityInspectorResponseDTO createQualityInspector(@RequestBody QualityInspectorRequestDTO request) {
        return qualityInspectorService.createQualityInspector(request);
    }

    @GetMapping
    public List<QualityInspectorResponseDTO> getAllQualityInspectors() {
        return qualityInspectorService.getAllQualityInspectors();
    }

    @GetMapping("/{userId}")
    public QualityInspectorResponseDTO getQualityInspectorByUserId(@PathVariable Long userId) {
        return qualityInspectorService.getQualityInspectorByUserId(userId);
    }

    @PutMapping("/{userId}")
    public QualityInspectorResponseDTO updateQualityInspector(@PathVariable Long userId, @RequestBody QualityInspectorRequestDTO request) {
        return qualityInspectorService.updateQualityInspector(userId, request);
    }

    // ================= Search APIs =================

    @GetMapping("/search/full-name")
    public List<QualityInspectorResponseDTO> searchByFullName(@RequestParam String fullName) {
        return qualityInspectorService.searchByFullName(fullName);
    }

    @GetMapping("/search/qualification")
    public List<QualityInspectorResponseDTO> searchByQualification(@RequestParam String qualification) {
        return qualityInspectorService.searchByQualification(qualification);
    }

    @GetMapping("/search/license-number")
    public List<QualityInspectorResponseDTO> searchByLicenseNumber(@RequestParam String licenseNumber) {
        return qualityInspectorService.searchByLicenseNumber(licenseNumber);
    }

    @GetMapping("/search/availability")
    public List<QualityInspectorResponseDTO> searchByAvailabilityStatus(@RequestParam String availabilityStatus) {
        return qualityInspectorService.searchByAvailabilityStatus(availabilityStatus);
    }

    @GetMapping("/search/email")
    public List<QualityInspectorResponseDTO> searchByEmail(@RequestParam String email) {
        return qualityInspectorService.searchByEmail(email);
    }

    @GetMapping("/search/phone-number")
    public List<QualityInspectorResponseDTO> searchByPhoneNumber(@RequestParam String phoneNumber) {
        return qualityInspectorService.searchByPhoneNumber(phoneNumber);
    }
}
