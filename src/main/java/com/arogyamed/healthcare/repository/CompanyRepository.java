package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByUserId(Long userId);

    // ================= Search Methods =================

    // Search by Company Name
    List<Company> findByCompanyNameContainingIgnoreCase(String companyName);

    // Search by License Number
    List<Company> findByLicenseNumberContainingIgnoreCase(String licenseNumber);

    // Search by GST Number
    List<Company> findByGstNumberContainingIgnoreCase(String gstNumber);

    // Search by Contact Person
    List<Company> findByContactPersonContainingIgnoreCase(String contactPerson);

    // Search by Company Address
    List<Company> findByCompanyAddressContainingIgnoreCase(String companyAddress);

    // Search by Email
    List<Company> findByUser_EmailContainingIgnoreCase(String email);

    // Search by Phone Number
    List<Company> findByUser_PhoneNumberContaining(String phoneNumber);

}
