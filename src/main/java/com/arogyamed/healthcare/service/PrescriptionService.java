package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PrescriptionRequestDTO;
import com.arogyamed.healthcare.dto.PrescriptionResponseDTO;

import java.util.List;

public interface PrescriptionService {

    PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO request);

    PrescriptionResponseDTO getPrescriptionById(Long id);

    PrescriptionResponseDTO updatePrescription(Long id, PrescriptionRequestDTO request);

    List<PrescriptionResponseDTO> getAllPrescriptions();

    // ================= Search =================

    List<PrescriptionResponseDTO> searchByPatientName(String fullName);

    List<PrescriptionResponseDTO> searchByDoctorName(String fullName);

    List<PrescriptionResponseDTO> searchByDiagnosis(String diagnosis);

    List<PrescriptionResponseDTO> searchByMedicine(String medicine);

    List<PrescriptionResponseDTO> searchByPrescriptionDate(java.time.LocalDate prescriptionDate);

    List<PrescriptionResponseDTO> searchByPrescriptionDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate);

    List<PrescriptionResponseDTO> searchByNotes(String notes);
}