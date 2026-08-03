package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Pharmacist;
import com.arogyamed.healthcare.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

    Optional<Pharmacist> findByUserId(Long userId);

    // ================= Search Methods =================

    List<Pharmacist> findByUser_FullNameContainingIgnoreCase(String fullName);

    List<Pharmacist> findByPharmacyNameContainingIgnoreCase(String pharmacyName);

    List<Pharmacist> findByLicenseNumberContainingIgnoreCase(String licenseNumber);

    List<Pharmacist> findByExperienceYearsGreaterThanEqual(Integer experienceYears);

    List<Pharmacist> findByPharmacyAddressContainingIgnoreCase(String pharmacyAddress);

    List<Pharmacist> findByUser_EmailContainingIgnoreCase(String email);

    List<Pharmacist> findByUser_PhoneNumberContaining(String phoneNumber);
    
}
