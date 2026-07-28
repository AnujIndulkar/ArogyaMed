package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // ================= Existing Methods =================

    Optional<Doctor> findByUserId(Long userId);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    // ================= Search Methods =================

    // Search by License Number
    List<Doctor> findByLicenseNumberContainingIgnoreCase(String licenseNumber);

    // Search by Doctor Name
    List<Doctor> findByUser_FullNameContainingIgnoreCase(String fullName);

    // Search by Specialization
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);

    // Search by Qualification
    List<Doctor> findByQualificationContainingIgnoreCase(String qualification);

    // Search by Experience
    List<Doctor> findByExperienceYearsGreaterThanEqual(Integer experienceYears);

    // Search by Hospital
    List<Doctor> findByHospitalNameContainingIgnoreCase(String hospitalName);

    // Search by Consultation Fee Range
    List<Doctor> findByConsultationFeeBetween(Double minFee, Double maxFee);

}
