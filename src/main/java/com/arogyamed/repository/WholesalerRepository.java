package com.arogyamed.repository;

import com.arogyamed.model.Wholesaler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface WholesalerRepository extends JpaRepository<Wholesaler, Long> {

    Optional<Wholesaler> findByUserId(Long userId);

    // ================= Search Methods =================

    List<Wholesaler> findByCompanyNameContainingIgnoreCase(String companyName);

    List<Wholesaler> findByLicenseNumberContainingIgnoreCase(String licenseNumber);

    List<Wholesaler> findByGstNumberContainingIgnoreCase(String gstNumber);

    List<Wholesaler> findByWarehouseAddressContainingIgnoreCase(String warehouseAddress);

    List<Wholesaler> findByContactPersonContainingIgnoreCase(String contactPerson);

    List<Wholesaler> findByUser_EmailContainingIgnoreCase(String email);

    List<Wholesaler> findByUser_PhoneNumberContaining(String phoneNumber);
}
