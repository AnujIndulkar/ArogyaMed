package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.AmbulanceRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceResponseDTO;
import com.arogyamed.healthcare.model.Ambulance;
import com.arogyamed.healthcare.repository.AmbulanceRepository;
import com.arogyamed.healthcare.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
