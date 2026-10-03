package com.arogyamed.service;

import com.arogyamed.dto.AmbulanceRequestDTO;
import com.arogyamed.dto.AmbulanceResponseDTO;
import com.arogyamed.model.AmbulanceStatus;

import java.time.LocalDate;
import java.util.List;

public interface AmbulanceService {

    AmbulanceResponseDTO createAmbulance(AmbulanceRequestDTO request);

    AmbulanceResponseDTO getAmbulanceById(Long id);

    AmbulanceResponseDTO getAmbulanceByUserId(Long userId);

    AmbulanceResponseDTO updateAmbulance(Long id, AmbulanceRequestDTO request);

    AmbulanceResponseDTO updateAmbulanceStatus(Long id, AmbulanceStatus status, boolean available);

    List<AmbulanceResponseDTO> getAllAmbulances();

    // ================= Search =================

    List<AmbulanceResponseDTO> searchByAmbulanceNumber(String ambulanceNumber);

    List<AmbulanceResponseDTO> searchByDriverName(String driverName);

    List<AmbulanceResponseDTO> searchByDriverPhone(String driverPhone);

    List<AmbulanceResponseDTO> searchByCurrentLocation(String currentLocation);

    List<AmbulanceResponseDTO> searchByStatus(AmbulanceStatus status);

    List<AmbulanceResponseDTO> searchByAvailability(Boolean available);

    List<AmbulanceResponseDTO> searchByRegistrationNumber(String registrationNumber);

    List<AmbulanceResponseDTO> searchByVerified(Boolean verified);

    List<AmbulanceResponseDTO> searchByInsuranceExpiry(LocalDate date);

    List<AmbulanceResponseDTO> searchByFitnessCertificateExpiry(LocalDate date);

    List<AmbulanceResponseDTO> searchByPollutionCertificateExpiry(LocalDate date);
}