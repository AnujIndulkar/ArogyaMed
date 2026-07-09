package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.SOSRequestDTO;
import com.arogyamed.healthcare.dto.SOSResponseDTO;
import com.arogyamed.healthcare.model.Patient;
import com.arogyamed.healthcare.model.SOS;
import com.arogyamed.healthcare.repository.PatientRepository;
import com.arogyamed.healthcare.repository.SOSRepository;
import com.arogyamed.healthcare.service.SOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SOSServiceImpl implements SOSService {

    @Autowired
    private SOSRepository sosRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public SOSResponseDTO createSOS(SOSRequestDTO request) {

        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        SOS sos = new SOS();

        sos.setPatient(patient);

        sos.setEmergencyType(request.getEmergencyType());

        sos.setLocation(request.getLocation());

        sos.setLatitude(request.getLatitude());

        sos.setLongitude(request.getLongitude());

        sos.setStatus(request.getStatus());

        sos.setCreatedAt(LocalDateTime.now());

        return mapToDTO(sosRepository.save(sos));
    }

    @Override
    public SOSResponseDTO getSOSById(Long id) {

        SOS sos = sosRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("SOS Request not found"));

        return mapToDTO(sos);
    }

    @Override
    public SOSResponseDTO updateSOS(Long id, SOSRequestDTO request) {

        SOS sos = sosRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("SOS Request not found"));

        sos.setEmergencyType(request.getEmergencyType());

        sos.setLocation(request.getLocation());

        sos.setLatitude(request.getLatitude());

        sos.setLongitude(request.getLongitude());

        sos.setStatus(request.getStatus());

        return mapToDTO(sosRepository.save(sos));
    }

    @Override
    public List<SOSResponseDTO> getAllSOS() {

        return sosRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private SOSResponseDTO mapToDTO(SOS sos) {

        SOSResponseDTO dto = new SOSResponseDTO();

        dto.setId(sos.getId());

        dto.setPatientId(sos.getPatient().getId());

        dto.setPatientName(sos.getPatient().getUser().getFullName());

        dto.setEmergencyType(sos.getEmergencyType());

        dto.setLocation(sos.getLocation());

        dto.setLatitude(sos.getLatitude());

        dto.setLongitude(sos.getLongitude());

        dto.setStatus(sos.getStatus());

        dto.setCreatedAt(sos.getCreatedAt());

        return dto;
    }
}