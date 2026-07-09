package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.MedicalRecordRequestDTO;
import com.arogyamed.healthcare.dto.MedicalRecordResponseDTO;
import com.arogyamed.healthcare.model.MedicalRecord;
import com.arogyamed.healthcare.model.Patient;
import com.arogyamed.healthcare.repository.MedicalRecordRepository;
import com.arogyamed.healthcare.repository.PatientRepository;
import com.arogyamed.healthcare.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordServiceImpl
        implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO request) {

        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setPatient(patient);
        medicalRecord.setDiagnosis(request.getDiagnosis());
        medicalRecord.setTreatment(request.getTreatment());
        medicalRecord.setDoctorNotes(request.getDoctorNotes());
        medicalRecord.setVisitDate(request.getVisitDate());

        return mapToDTO(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public MedicalRecordResponseDTO getMedicalRecordById(Long id) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() ->
                                new RuntimeException("Medical Record not found"));

        return mapToDTO(medicalRecord);
    }

    @Override
    public MedicalRecordResponseDTO updateMedicalRecord(Long id, MedicalRecordRequestDTO request) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() ->
                                new RuntimeException("Medical Record not found"));

        medicalRecord.setDiagnosis(request.getDiagnosis());
        medicalRecord.setTreatment(request.getTreatment());
        medicalRecord.setDoctorNotes(request.getDoctorNotes());
        medicalRecord.setVisitDate(request.getVisitDate());

        return mapToDTO(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {

        return medicalRecordRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private MedicalRecordResponseDTO mapToDTO(MedicalRecord medicalRecord) {

        MedicalRecordResponseDTO dto = new MedicalRecordResponseDTO();

        dto.setId(medicalRecord.getId());

        dto.setPatientId(medicalRecord.getPatient().getId());

        dto.setPatientName(medicalRecord.getPatient().getUser().getFullName());

        dto.setDiagnosis(medicalRecord.getDiagnosis());

        dto.setTreatment(medicalRecord.getTreatment());

        dto.setDoctorNotes(medicalRecord.getDoctorNotes());

        dto.setVisitDate(medicalRecord.getVisitDate());

        return dto;
    }
}
