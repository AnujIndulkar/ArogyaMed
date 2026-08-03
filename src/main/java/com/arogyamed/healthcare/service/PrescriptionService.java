package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PrescriptionRequestDTO;
import com.arogyamed.healthcare.dto.PrescriptionResponseDTO;
import com.arogyamed.healthcare.model.PrescriptionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionService {

    PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO request);

    PrescriptionResponseDTO getPrescriptionById(Long id);

    PrescriptionResponseDTO updatePrescription(Long id, PrescriptionRequestDTO request);

    List<PrescriptionResponseDTO> getAllPrescriptions();

    List<PrescriptionResponseDTO> searchByPatientName(String fullName);

    List<PrescriptionResponseDTO> searchByDoctorName(String fullName);

    List<PrescriptionResponseDTO> searchByDiagnosis(String diagnosis);

    List<PrescriptionResponseDTO> searchByMedicine(String medicine);

    List<PrescriptionResponseDTO> searchByPrescriptionDate(LocalDate prescriptionDate);

    List<PrescriptionResponseDTO> searchByPrescriptionDateRange(LocalDate startDate, LocalDate endDate);

    List<PrescriptionResponseDTO> searchByNotes(String notes);

    // ================= Patient upload + pharmacist verification =================

    PrescriptionResponseDTO uploadPrescription(
            Long patientId,
            String doctorName,
            String clinicName,
            String notes,
            MultipartFile file
    );

    PrescriptionResponseDTO updateStatus(Long id, PrescriptionStatus status, String rejectionReason);

    List<PrescriptionResponseDTO> getByPatientId(Long patientId);
}