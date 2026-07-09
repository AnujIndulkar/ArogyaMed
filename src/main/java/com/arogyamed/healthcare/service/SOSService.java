package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.SOSRequestDTO;
import com.arogyamed.healthcare.dto.SOSResponseDTO;

import java.util.List;

public interface SOSService {

    SOSResponseDTO createSOS(SOSRequestDTO request);

    SOSResponseDTO getSOSById(Long id);

    SOSResponseDTO updateSOS(Long id, SOSRequestDTO request);

    List<SOSResponseDTO> getAllSOS();
}
