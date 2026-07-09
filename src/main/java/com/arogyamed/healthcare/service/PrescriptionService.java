package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PrescriptionRequestDTO;
import com.arogyamed.healthcare.dto.PrescriptionResponseDTO;

import java.util.List;

public interface PrescriptionService {

    PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO request);

    PrescriptionResponseDTO getPrescriptionById(Long id);

    PrescriptionResponseDTO updatePrescription(Long id, PrescriptionRequestDTO request);

    List<PrescriptionResponseDTO> getAllPrescriptions();
}