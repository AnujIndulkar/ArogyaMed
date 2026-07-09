package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.PrescriptionRequestDTO;
import com.arogyamed.healthcare.dto.PrescriptionResponseDTO;
import com.arogyamed.healthcare.model.Doctor;
import com.arogyamed.healthcare.model.Patient;
import com.arogyamed.healthcare.model.Prescription;
import com.arogyamed.healthcare.repository.DoctorRepository;
import com.arogyamed.healthcare.repository.PatientRepository;
import com.arogyamed.healthcare.repository.PrescriptionRepository;
import com.arogyamed.healthcare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PrescriptionResponseDTO createPrescription(
            PrescriptionRequestDTO request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        Prescription prescription = new Prescription();

        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setMedicines(request.getMedicines());
        prescription.setDosageInstructions(request.getDosageInstructions());
        prescription.setPrescriptionDate(request.getPrescriptionDate());
        prescription.setNotes(request.getNotes());

        return mapToDTO(prescriptionRepository.save(prescription));
    }

    @Override
    public PrescriptionResponseDTO getPrescriptionById(Long id) {

        Prescription prescription = prescriptionRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Prescription not found"));

        return mapToDTO(prescription);
    }

    @Override
    public PrescriptionResponseDTO updatePrescription(Long id, PrescriptionRequestDTO request) {

        Prescription prescription = prescriptionRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Prescription not found"));

        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setMedicines(request.getMedicines());
        prescription.setDosageInstructions(request.getDosageInstructions());
        prescription.setPrescriptionDate(request.getPrescriptionDate());
        prescription.setNotes(request.getNotes());

        return mapToDTO(prescriptionRepository.save(prescription));
    }

    @Override
    public List<PrescriptionResponseDTO> getAllPrescriptions() {

        return prescriptionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PrescriptionResponseDTO mapToDTO(Prescription prescription) {

        PrescriptionResponseDTO dto = new PrescriptionResponseDTO();

        dto.setId(prescription.getId());

        dto.setDoctorId(prescription.getDoctor().getId());

        dto.setDoctorName(prescription.getDoctor().getUser().getFullName());

        dto.setPatientId(prescription.getPatient().getId());

        dto.setPatientName(prescription.getPatient().getUser().getFullName());

        dto.setDiagnosis(prescription.getDiagnosis());

        dto.setMedicines(prescription.getMedicines());

        dto.setDosageInstructions(prescription.getDosageInstructions());

        dto.setPrescriptionDate(prescription.getPrescriptionDate());

        dto.setNotes(prescription.getNotes());

        return dto;
    }
}
