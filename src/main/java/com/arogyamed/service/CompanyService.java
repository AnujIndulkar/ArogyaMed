package com.arogyamed.service;

import com.arogyamed.dto.CompanyRequestDTO;
import com.arogyamed.dto.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {

    // ================= CRUD =================

    CompanyResponseDTO createCompany(CompanyRequestDTO request);

    CompanyResponseDTO getCompanyByUserId(Long userId);

    CompanyResponseDTO updateCompany(Long userId, CompanyRequestDTO request);

    // ================= Search =================

    // Search by Company Name
    List<CompanyResponseDTO> searchByCompanyName(String companyName);

    // Search by License Number
    List<CompanyResponseDTO> searchByLicenseNumber(String licenseNumber);

    // Search by GST Number
    List<CompanyResponseDTO> searchByGstNumber(String gstNumber);

    // Search by Contact Person
    List<CompanyResponseDTO> searchByContactPerson(String contactPerson);

    // Search by Company Address
    List<CompanyResponseDTO> searchByCompanyAddress(String companyAddress);

    // Search by Email
    List<CompanyResponseDTO> searchByEmail(String email);

    // Search by Phone Number
    List<CompanyResponseDTO> searchByPhoneNumber(String phoneNumber);
}

