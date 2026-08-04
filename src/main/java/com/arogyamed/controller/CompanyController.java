package com.arogyamed.controller;

import com.arogyamed.dto.CompanyRequestDTO;
import com.arogyamed.dto.CompanyResponseDTO;
import com.arogyamed.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public CompanyResponseDTO createCompany(@RequestBody CompanyRequestDTO request) {
        return companyService.createCompany(request);
    }

    @GetMapping("/{userId}")
    public CompanyResponseDTO getCompanyByUserId(@PathVariable Long userId) {
        return companyService.getCompanyByUserId(userId);
    }

    // ================= Search APIs =================

    // Search by Company Name
    @GetMapping("/search/company-name")
    public List<CompanyResponseDTO> searchByCompanyName(@RequestParam String companyName) {
        return companyService.searchByCompanyName(companyName);
    }

    // Search by License Number
    @GetMapping("/search/license-number")
    public List<CompanyResponseDTO> searchByLicenseNumber(@RequestParam String licenseNumber) {
        return companyService.searchByLicenseNumber(licenseNumber);
    }

    // Search by GST Number
    @GetMapping("/search/gst-number")
    public List<CompanyResponseDTO> searchByGstNumber(@RequestParam String gstNumber) {
        return companyService.searchByGstNumber(gstNumber);
    }

    // Search by Contact Person
    @GetMapping("/search/contact-person")
    public List<CompanyResponseDTO> searchByContactPerson(@RequestParam String contactPerson) {
        return companyService.searchByContactPerson(contactPerson);
    }

    // Search by Company Address
    @GetMapping("/search/address")
    public List<CompanyResponseDTO> searchByCompanyAddress(@RequestParam String companyAddress) {
        return companyService.searchByCompanyAddress(companyAddress);
    }

    // Search by Email
    @GetMapping("/search/email")
    public List<CompanyResponseDTO> searchByEmail(@RequestParam String email) {
        return companyService.searchByEmail(email);
    }

    // Search by Phone Number
    @GetMapping("/search/phone-number")
    public List<CompanyResponseDTO> searchByPhoneNumber(@RequestParam String phoneNumber) {
        return companyService.searchByPhoneNumber(phoneNumber);
    }

    @PutMapping("/{userId}")
    public CompanyResponseDTO updateCompany(@PathVariable Long userId, @RequestBody CompanyRequestDTO request) {
        return companyService.updateCompany(userId, request);
    }
}
