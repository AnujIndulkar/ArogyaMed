package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.MedicalRecordRequestDTO;
import com.arogyamed.healthcare.dto.MedicalRecordResponseDTO;
import java.time.LocalDate;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO request);

    MedicalRecordResponseDTO getMedicalRecordById(Long id);

    MedicalRecordResponseDTO updateMedicalRecord(Long id, MedicalRecordRequestDTO request);

    List<MedicalRecordResponseDTO> getAllMedicalRecords();

    // ================= Search =================

    List<MedicalRecordResponseDTO> searchByPatientName(String fullName);

    List<MedicalRecordResponseDTO> searchByDiagnosis(String diagnosis);

    List<MedicalRecordResponseDTO> searchByTreatment(String treatment);

    List<MedicalRecordResponseDTO> searchByDoctorNotes(String doctorNotes);

    List<MedicalRecordResponseDTO> searchByVisitDate(LocalDate visitDate);

    List<MedicalRecordResponseDTO> searchByVisitDateRange(LocalDate startDate, LocalDate endDate);
}
