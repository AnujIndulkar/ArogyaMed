package com.arogyamed.repository;

import com.arogyamed.model.QualityInspector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QualityInspectorRepository extends JpaRepository<QualityInspector, Long> {

    Optional<QualityInspector> findByUserId(Long userId);

    // the logged-in inspector, found by the email in their login token
    Optional<QualityInspector> findByUser_Email(String email);

    // ================= Search Methods =================

    List<QualityInspector> findByUser_FullNameContainingIgnoreCase(String fullName);

    List<QualityInspector> findByQualificationContainingIgnoreCase(String qualification);

    List<QualityInspector> findByLicenseNumberContainingIgnoreCase(String licenseNumber);

    List<QualityInspector> findByAvailabilityStatusContainingIgnoreCase(String availabilityStatus);

    List<QualityInspector> findByUser_EmailContainingIgnoreCase(String email);

    List<QualityInspector> findByUser_PhoneNumberContaining(String phoneNumber);
}