package com.arogyamed.service.impl;

import com.arogyamed.dto.PrescriptionRequestDTO;
import com.arogyamed.dto.PrescriptionResponseDTO;
import com.arogyamed.model.Doctor;
import com.arogyamed.model.Patient;
import com.arogyamed.model.Prescription;
import com.arogyamed.model.PrescriptionStatus;
import com.arogyamed.repository.DoctorRepository;
import com.arogyamed.repository.PatientRepository;
import com.arogyamed.repository.PrescriptionRepository;
import com.arogyamed.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Value("${media.upload.path}")
    private String mediaUploadPath;

    @Override
    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO request) {

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
        prescription.setStatus(PrescriptionStatus.VERIFIED); // doctor-issued, no review needed

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

    // ================= Patient upload + pharmacist verification =================

    @Override
    public PrescriptionResponseDTO uploadPrescription(
            Long patientId, String doctorName, String clinicName, String notes, MultipartFile file) {

        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new RuntimeException("Patient not found"));

        try {

            String originalFileName = file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String storedFileName = UUID.randomUUID() + extension;

            Path directoryPath = Paths.get(mediaUploadPath, "prescriptions");

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path targetPath = directoryPath.resolve(storedFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            Prescription prescription = new Prescription();

            prescription.setPatient(patient);
            prescription.setDoctorName(doctorName);
            prescription.setClinicName(clinicName);
            prescription.setNotes(notes);
            prescription.setPrescriptionImageUrl("/files/prescriptions/" + storedFileName);
            prescription.setPrescriptionDate(LocalDate.now());
            prescription.setStatus(PrescriptionStatus.PENDING);
            prescription.setUploadedAt(LocalDateTime.now());

            return mapToDTO(prescriptionRepository.save(prescription));

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload prescription.", e);
        }
    }

    @Override
    public PrescriptionResponseDTO updateStatus(Long id, PrescriptionStatus status, String rejectionReason) {

        Prescription prescription = prescriptionRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Prescription not found"));

        prescription.setStatus(status);
        prescription.setRejectionReason(status == PrescriptionStatus.REJECTED ? rejectionReason : null);

        return mapToDTO(prescriptionRepository.save(prescription));
    }

    @Override
    public List<PrescriptionResponseDTO> getByPatientId(Long patientId) {

        return prescriptionRepository.findByPatient_Id(patientId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= Mapper =================

    private PrescriptionResponseDTO mapToDTO(Prescription prescription) {

        PrescriptionResponseDTO dto = new PrescriptionResponseDTO();

        dto.setId(prescription.getId());

        if (prescription.getDoctor() != null) {
            dto.setDoctorId(prescription.getDoctor().getId());
            dto.setDoctorName(prescription.getDoctor().getUser().getFullName());
        } else {
            dto.setDoctorName(prescription.getDoctorName());
        }

        dto.setPatientId(prescription.getPatient().getId());

        dto.setPatientName(prescription.getPatient().getUser().getFullName());

        dto.setDiagnosis(prescription.getDiagnosis());

        dto.setMedicines(prescription.getMedicines());

        dto.setDosageInstructions(prescription.getDosageInstructions());

        dto.setPrescriptionDate(prescription.getPrescriptionDate());

        dto.setNotes(prescription.getNotes());

        dto.setPrescriptionImageUrl(prescription.getPrescriptionImageUrl());

        dto.setClinicName(prescription.getClinicName());

        dto.setStatus(prescription.getStatus());

        dto.setRejectionReason(prescription.getRejectionReason());

        dto.setUploadedAt(prescription.getUploadedAt());

        return dto;
    }

    // ================= Search (unchanged) =================

    @Override
    public List<PrescriptionResponseDTO> searchByPatientName(String fullName) {
        return mapToDTOList(prescriptionRepository.findByPatient_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<PrescriptionResponseDTO> searchByDoctorName(String fullName) {
        return mapToDTOList(prescriptionRepository.findByDoctor_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<PrescriptionResponseDTO> searchByDiagnosis(String diagnosis) {
        return mapToDTOList(prescriptionRepository.findByDiagnosisContainingIgnoreCase(diagnosis));
    }

    @Override
    public List<PrescriptionResponseDTO> searchByMedicine(String medicine) {
        return mapToDTOList(prescriptionRepository.findByMedicinesContainingIgnoreCase(medicine));
    }

    @Override
    public List<PrescriptionResponseDTO> searchByPrescriptionDate(LocalDate prescriptionDate) {
        return mapToDTOList(prescriptionRepository.findByPrescriptionDate(prescriptionDate));
    }

    @Override
    public List<PrescriptionResponseDTO> searchByPrescriptionDateRange(LocalDate startDate, LocalDate endDate) {
        return mapToDTOList(prescriptionRepository.findByPrescriptionDateBetween(startDate, endDate));
    }

    @Override
    public List<PrescriptionResponseDTO> searchByNotes(String notes) {
        return mapToDTOList(prescriptionRepository.findByNotesContainingIgnoreCase(notes));
    }

    private List<PrescriptionResponseDTO> mapToDTOList(List<Prescription> prescriptions) {
        return prescriptions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}