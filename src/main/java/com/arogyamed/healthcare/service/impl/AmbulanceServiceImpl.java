package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.AmbulanceRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceResponseDTO;
import com.arogyamed.healthcare.model.Ambulance;
import com.arogyamed.healthcare.model.AmbulanceStatus;
import com.arogyamed.healthcare.repository.AmbulanceRepository;
import com.arogyamed.healthcare.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbulanceServiceImpl implements AmbulanceService {

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Override
    public AmbulanceResponseDTO createAmbulance(AmbulanceRequestDTO request) {

        Ambulance ambulance = new Ambulance();

        ambulance.setAmbulanceNumber(request.getAmbulanceNumber());

        ambulance.setDriverName(request.getDriverName());

        ambulance.setDriverPhone(request.getDriverPhone());

        ambulance.setCurrentLocation(request.getCurrentLocation());

        ambulance.setStatus(request.getStatus());

        ambulance.setAvailable(request.isAvailable());

        return mapToDTO(ambulanceRepository.save(ambulance));
    }

    @Override
    public AmbulanceResponseDTO getAmbulanceById(Long id) {

        Ambulance ambulance = ambulanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Ambulance not found"));

        return mapToDTO(ambulance);
    }

    @Override
    public AmbulanceResponseDTO updateAmbulance(Long id, AmbulanceRequestDTO request) {

        Ambulance ambulance = ambulanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Ambulance not found"));

        ambulance.setAmbulanceNumber(request.getAmbulanceNumber());

        ambulance.setDriverName(request.getDriverName());

        ambulance.setDriverPhone(request.getDriverPhone());

        ambulance.setCurrentLocation(request.getCurrentLocation());

        ambulance.setStatus(request.getStatus());

        ambulance.setAvailable(request.isAvailable());

        return mapToDTO(ambulanceRepository.save(ambulance));
    }

    @Override
    public List<AmbulanceResponseDTO> getAllAmbulances() {

        return ambulanceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AmbulanceResponseDTO mapToDTO(Ambulance ambulance) {

        AmbulanceResponseDTO dto = new AmbulanceResponseDTO();

        dto.setId(ambulance.getId());

        dto.setAmbulanceNumber(ambulance.getAmbulanceNumber());

        dto.setDriverName(ambulance.getDriverName());

        dto.setDriverPhone(ambulance.getDriverPhone());

        dto.setCurrentLocation(ambulance.getCurrentLocation());

        dto.setStatus(ambulance.getStatus());

        dto.setAvailable(ambulance.isAvailable());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<AmbulanceResponseDTO> searchByAmbulanceNumber(String ambulanceNumber) {

        return mapToDTOList(ambulanceRepository.findByAmbulanceNumberContainingIgnoreCase(ambulanceNumber));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByDriverName(String driverName) {

        return mapToDTOList(ambulanceRepository.findByDriverNameContainingIgnoreCase(driverName));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByDriverPhone(String driverPhone) {

        return mapToDTOList(ambulanceRepository.findByDriverPhoneContaining(driverPhone));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByCurrentLocation(String currentLocation) {

        return mapToDTOList(ambulanceRepository.findByCurrentLocationContainingIgnoreCase(currentLocation));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByStatus(AmbulanceStatus status) {

        return mapToDTOList(ambulanceRepository.findByStatus(status));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByAvailability(Boolean available) {

        return mapToDTOList(ambulanceRepository.findByAvailable(available));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByRegistrationNumber(String registrationNumber) {

        return mapToDTOList(ambulanceRepository.findByRegistrationNumberContainingIgnoreCase(registrationNumber));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByVerified(Boolean verified) {

        return mapToDTOList(ambulanceRepository.findByVerified(verified));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByInsuranceExpiry(LocalDate date) {

        return mapToDTOList(ambulanceRepository.findByInsuranceExpiryDateBefore(date));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByFitnessCertificateExpiry(LocalDate date) {

        return mapToDTOList(ambulanceRepository.findByFitnessCertificateExpiryDateBefore(date));
    }

    @Override
    public List<AmbulanceResponseDTO> searchByPollutionCertificateExpiry(LocalDate date) {

        return mapToDTOList(ambulanceRepository.findByPollutionExpiryDateBefore(date));
    }

    private List<AmbulanceResponseDTO> mapToDTOList(List<Ambulance> ambulances) {

        return ambulances.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
