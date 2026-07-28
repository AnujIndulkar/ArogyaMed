package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PharmacistRequestDTO;
import com.arogyamed.healthcare.dto.PharmacistResponseDTO;

import java.util.List;

public interface PharmacistService {

    // ================= CRUD =================

    PharmacistResponseDTO createPharmacist(PharmacistRequestDTO request);

    PharmacistResponseDTO getPharmacistByUserId(Long userId);

    PharmacistResponseDTO updatePharmacist(Long userId, PharmacistRequestDTO request);

    // ================= Search =================

    // Search by Pharmacist Name
    List<PharmacistResponseDTO> searchByFullName(String fullName);

    // Search by Pharmacy Name
    List<PharmacistResponseDTO> searchByPharmacyName(String pharmacyName);

    // Search by License Number
    List<PharmacistResponseDTO> searchByLicenseNumber(String licenseNumber);

    // Search by Experience
    List<PharmacistResponseDTO> searchByExperienceYears(Integer experienceYears);

    // Search by Pharmacy Address
    List<PharmacistResponseDTO> searchByPharmacyAddress(String pharmacyAddress);

    // Search by Email
    List<PharmacistResponseDTO> searchByEmail(String email);

    // Search by Phone Number
    List<PharmacistResponseDTO> searchByPhoneNumber(String phoneNumber);
}
