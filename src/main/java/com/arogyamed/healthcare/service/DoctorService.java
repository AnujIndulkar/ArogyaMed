package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.DoctorRequestDTO;
import com.arogyamed.healthcare.dto.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {

    // ================= CRUD =================

    DoctorResponseDTO createDoctor(DoctorRequestDTO request);

    DoctorResponseDTO getDoctorByUserId(Long userId);

    DoctorResponseDTO updateDoctor(Long userId, DoctorRequestDTO request);


    // ================= Search =================

    // Search by Doctor Name
    List<DoctorResponseDTO> searchByDoctorName(String fullName);

    // Search by Specialization
    List<DoctorResponseDTO> searchBySpecialization(String specialization);

    // Search by Qualification
    List<DoctorResponseDTO> searchByQualification(String qualification);

    // Search by Experience
    List<DoctorResponseDTO> searchByExperience(Integer experienceYears);

    // Search by Hospital
    List<DoctorResponseDTO> searchByHospital(String hospitalName);

    // Search by Consultation Fee Range
    List<DoctorResponseDTO> searchByConsultationFee(Double minFee, Double maxFee);

    // Search by License Number
    List<DoctorResponseDTO> searchByLicenseNumber(String licenseNumber);

}