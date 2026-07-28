package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.PharmacistRequestDTO;
import com.arogyamed.healthcare.dto.PharmacistResponseDTO;
import com.arogyamed.healthcare.service.PharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacists")
public class PharmacistController {

    @Autowired
    private PharmacistService pharmacistService;

    @PostMapping
    public PharmacistResponseDTO createPharmacist(@RequestBody PharmacistRequestDTO request) {
        return pharmacistService.createPharmacist(request);
    }

    @GetMapping("/{userId}")
    public PharmacistResponseDTO getPharmacistByUserId(@PathVariable Long userId) {
        return pharmacistService.getPharmacistByUserId(userId);
    }

    // ================= Search APIs =================

    // Search by Pharmacist Name
    @GetMapping("/search/full-name")
    public List<PharmacistResponseDTO> searchByFullName(@RequestParam String fullName) {
        return pharmacistService.searchByFullName(fullName);
    }

    // Search by Pharmacy Name
    @GetMapping("/search/pharmacy-name")
    public List<PharmacistResponseDTO> searchByPharmacyName(@RequestParam String pharmacyName) {
        return pharmacistService.searchByPharmacyName(pharmacyName);
    }

    // Search by License Number
    @GetMapping("/search/license-number")
    public List<PharmacistResponseDTO> searchByLicenseNumber(@RequestParam String licenseNumber) {
        return pharmacistService.searchByLicenseNumber(licenseNumber);
    }

    // Search by Experience
    @GetMapping("/search/experience")
    public List<PharmacistResponseDTO> searchByExperienceYears(@RequestParam Integer experienceYears) {
        return pharmacistService.searchByExperienceYears(experienceYears);
    }

    // Search by Pharmacy Address
    @GetMapping("/search/address")
    public List<PharmacistResponseDTO> searchByPharmacyAddress(@RequestParam String pharmacyAddress) {
        return pharmacistService.searchByPharmacyAddress(pharmacyAddress);
    }

    // Search by Email
    @GetMapping("/search/email")
    public List<PharmacistResponseDTO> searchByEmail(@RequestParam String email) {
        return pharmacistService.searchByEmail(email);
    }

    // Search by Phone Number
    @GetMapping("/search/phone-number")
    public List<PharmacistResponseDTO> searchByPhoneNumber(@RequestParam String phoneNumber) {
        return pharmacistService.searchByPhoneNumber(phoneNumber);
    }

    @PutMapping("/{userId}")
    public PharmacistResponseDTO updatePharmacist(@PathVariable Long userId, @RequestBody PharmacistRequestDTO request) {
        return pharmacistService.updatePharmacist(userId, request);
    }


}
