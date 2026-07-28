package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUserId(Long userId);

    // ================= Search =================

    // User Details
    List<Patient> findByUserFullNameContainingIgnoreCase(String fullName);

    List<Patient> findByUserEmailContainingIgnoreCase(String email);

    List<Patient> findByUserPhoneNumberContaining(String phoneNumber);

    // Basic Details
    List<Patient> findByGenderIgnoreCase(String gender);

    List<Patient> findByBloodGroupIgnoreCase(String bloodGroup);

    List<Patient> findByAge(Integer age);

    List<Patient> findByDateOfBirth(LocalDate dateOfBirth);

    // Medical Details
    List<Patient> findByAllergiesContainingIgnoreCase(String allergies);

    List<Patient> findByMedicalHistoryContainingIgnoreCase(String medicalHistory);

    // Emergency Contact
    List<Patient> findByEmergencyContactNameContainingIgnoreCase(String emergencyContactName);

    List<Patient> findByEmergencyContactNumberContaining(String emergencyContactNumber);

    // Insurance
    List<Patient> findByInsuranceProviderContainingIgnoreCase(String insuranceProvider);

    List<Patient> findByInsurancePolicyNumber(String insurancePolicyNumber);

    // Personal Details
    List<Patient> findByOccupationContainingIgnoreCase(String occupation);

    List<Patient> findByMaritalStatusIgnoreCase(String maritalStatus);

    // Address
    List<Patient> findByCityContainingIgnoreCase(String city);

    List<Patient> findByDistrictContainingIgnoreCase(String district);

    List<Patient> findByStateContainingIgnoreCase(String state);

    List<Patient> findByCountryContainingIgnoreCase(String country);

    List<Patient> findByPincode(String pincode);

    List<Patient> findByUserPhoneNumberContainingIgnoreCase(String phoneNumber);

    List<Patient> findByGenderContainingIgnoreCase(String gender);

    List<Patient> findByBloodGroupContainingIgnoreCase(String bloodGroup);

    List<Patient> findByEmergencyContactNumberContainingIgnoreCase(String emergencyContactNumber);

    List<Patient> findByInsurancePolicyNumberContainingIgnoreCase(String insurancePolicyNumber);

    List<Patient> findByMaritalStatusContainingIgnoreCase(String maritalStatus);

    List<Patient> findByPincodeContainingIgnoreCase(String pincode);
}
