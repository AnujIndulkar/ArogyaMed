package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PatientRequestDTO;
import com.arogyamed.healthcare.dto.PatientResponseDTO;

public interface PatientService {

    PatientResponseDTO createPatient(PatientRequestDTO request);

    PatientResponseDTO getPatientByUserId(Long userId);

    PatientResponseDTO updatePatient(Long userId, PatientRequestDTO request);
}
