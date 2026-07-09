package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.PatientRequestDTO;
import com.arogyamed.healthcare.dto.PatientResponseDTO;
import com.arogyamed.healthcare.model.Patient;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.PatientRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                        new RuntimeException("User not found"));

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setHeight(request.getHeight());
        patient.setWeight(request.getWeight());
        patient.setAllergies(request.getAllergies());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setDateOfBirth(request.getDateOfBirth());

        return mapToDTO(patientRepository.save(patient));
    }

    @Override
    public PatientResponseDTO getPatientByUserId(Long userId) {

        Patient patient = patientRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        return mapToDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(Long userId, PatientRequestDTO request) {

        Patient patient = patientRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setHeight(request.getHeight());
        patient.setWeight(request.getWeight());
        patient.setAllergies(request.getAllergies());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setDateOfBirth(request.getDateOfBirth());

        return mapToDTO(patientRepository.save(patient));
    }

    private PatientResponseDTO mapToDTO(Patient patient) {

        PatientResponseDTO dto = new PatientResponseDTO();

        dto.setId(patient.getId());
        dto.setUserId(patient.getUser().getId());
        dto.setFullName(patient.getUser().getFullName());
        dto.setEmail(patient.getUser().getEmail());

        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setBloodGroup(patient.getBloodGroup());
        dto.setHeight(patient.getHeight());
        dto.setWeight(patient.getWeight());
        dto.setAllergies(patient.getAllergies());
        dto.setMedicalHistory(patient.getMedicalHistory());
        dto.setDateOfBirth(patient.getDateOfBirth());

        return dto;
    }
}
