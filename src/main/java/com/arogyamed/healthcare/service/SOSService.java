package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.SOSRequestDTO;
import com.arogyamed.healthcare.dto.SOSResponseDTO;
import com.arogyamed.healthcare.model.SOSStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface SOSService {

    SOSResponseDTO createSOS(SOSRequestDTO request);

    SOSResponseDTO getSOSById(Long id);

    SOSResponseDTO updateSOS(Long id, SOSRequestDTO request);

    List<SOSResponseDTO> getAllSOS();

    // ================= Search =================

    List<SOSResponseDTO> searchByPatientName(String fullName);

    List<SOSResponseDTO> searchByEmergencyType(String emergencyType);

    List<SOSResponseDTO> searchByLocation(String location);

    List<SOSResponseDTO> searchByStatus(SOSStatus status);

    List<SOSResponseDTO> searchByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate);
}