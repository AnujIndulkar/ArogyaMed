package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PatientRequestDTO;
import com.arogyamed.healthcare.dto.PatientResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    PatientResponseDTO createPatient(PatientRequestDTO request);

    PatientResponseDTO getPatientByUserId(Long userId);

    PatientResponseDTO updatePatient(Long userId, PatientRequestDTO request);

    // ================= Search =================

    List<PatientResponseDTO> searchByName(String name);

    List<PatientResponseDTO> searchByEmail(String email);

    List<PatientResponseDTO> searchByPhone(String phoneNumber);

    List<PatientResponseDTO> searchByGender(String gender);

    List<PatientResponseDTO> searchByBloodGroup(String bloodGroup);

    List<PatientResponseDTO> searchByAge(Integer age);

    List<PatientResponseDTO> searchByDateOfBirth(LocalDate dateOfBirth);

    List<PatientResponseDTO> searchByAllergies(String allergies);

    List<PatientResponseDTO> searchByMedicalHistory(String medicalHistory);

    List<PatientResponseDTO> searchByEmergencyContactName(String emergencyContactName);

    List<PatientResponseDTO> searchByEmergencyContactNumber(String emergencyContactNumber);

    List<PatientResponseDTO> searchByInsuranceProvider(String insuranceProvider);

    List<PatientResponseDTO> searchByInsurancePolicyNumber(String insurancePolicyNumber);

    List<PatientResponseDTO> searchByOccupation(String occupation);

    List<PatientResponseDTO> searchByMaritalStatus(String maritalStatus);

    List<PatientResponseDTO> searchByCity(String city);

    List<PatientResponseDTO> searchByDistrict(String district);

    List<PatientResponseDTO> searchByState(String state);

    List<PatientResponseDTO> searchByCountry(String country);

    List<PatientResponseDTO> searchByPincode(String pincode);
}
