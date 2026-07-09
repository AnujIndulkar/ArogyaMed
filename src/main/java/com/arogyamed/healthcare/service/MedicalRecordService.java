package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.MedicalRecordRequestDTO;
import com.arogyamed.healthcare.dto.MedicalRecordResponseDTO;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO request);

    MedicalRecordResponseDTO getMedicalRecordById(Long id);

    MedicalRecordResponseDTO updateMedicalRecord(Long id, MedicalRecordRequestDTO request);

    List<MedicalRecordResponseDTO> getAllMedicalRecords();
}
