package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AmbulanceRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceResponseDTO;

import java.util.List;

public interface AmbulanceService {

    AmbulanceResponseDTO createAmbulance(AmbulanceRequestDTO request);

    AmbulanceResponseDTO getAmbulanceById(Long id);

    AmbulanceResponseDTO updateAmbulance(Long id, AmbulanceRequestDTO request);

    List<AmbulanceResponseDTO> getAllAmbulances();
}
