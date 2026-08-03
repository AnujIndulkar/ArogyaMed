package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.PrescriptionRequestDTO;
import com.arogyamed.healthcare.dto.PrescriptionResponseDTO;
import com.arogyamed.healthcare.model.PrescriptionStatus;
import com.arogyamed.healthcare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public PrescriptionResponseDTO createPrescription(@RequestBody PrescriptionRequestDTO request) {
        return prescriptionService.createPrescription(request);
    }

    @GetMapping("/{id}")
    public PrescriptionResponseDTO getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id);
    }

    @PutMapping("/{id}")
    public PrescriptionResponseDTO updatePrescription(@PathVariable Long id, @RequestBody PrescriptionRequestDTO request) {
        return prescriptionService.updatePrescription(id, request);
    }

    @GetMapping
    public List<PrescriptionResponseDTO> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    // ================= Search =================

    // Search by Patient Name
    @GetMapping("/search/patient")
    public List<PrescriptionResponseDTO> searchByPatientName(@RequestParam String fullName) {

        return prescriptionService.searchByPatientName(fullName);
    }

    // Search by Doctor Name
    @GetMapping("/search/doctor")
    public List<PrescriptionResponseDTO> searchByDoctorName(@RequestParam String fullName) {

        return prescriptionService.searchByDoctorName(fullName);
    }

    // Search by Diagnosis
    @GetMapping("/search/diagnosis")
    public List<PrescriptionResponseDTO> searchByDiagnosis(@RequestParam String diagnosis) {

        return prescriptionService.searchByDiagnosis(diagnosis);
    }

    // Search by Medicine
    @GetMapping("/search/medicine")
    public List<PrescriptionResponseDTO> searchByMedicine(@RequestParam String medicine) {

        return prescriptionService.searchByMedicine(medicine);
    }

    // Search by Prescription Date
    @GetMapping("/search/date")
    public List<PrescriptionResponseDTO> searchByPrescriptionDate(@RequestParam java.time.LocalDate prescriptionDate) {

        return prescriptionService.searchByPrescriptionDate(prescriptionDate);
    }

    // Search by Date Range
    @GetMapping("/search/date-range")
    public List<PrescriptionResponseDTO> searchByPrescriptionDateRange(@RequestParam java.time.LocalDate startDate, @RequestParam java.time.LocalDate endDate) {

        return prescriptionService.searchByPrescriptionDateRange(startDate, endDate);
    }

    // Search by Notes
    @GetMapping("/search/notes")
    public List<PrescriptionResponseDTO> searchByNotes(@RequestParam String notes) {

        return prescriptionService.searchByNotes(notes);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public PrescriptionResponseDTO uploadPrescription(
            @RequestParam Long patientId,
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) String clinicName,
            @RequestParam(required = false) String notes,
            @RequestPart("file") MultipartFile file) {

        return prescriptionService.uploadPrescription(patientId, doctorName, clinicName, notes, file);
    }

    @PutMapping("/{id}/status")
    public PrescriptionResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam PrescriptionStatus status,
            @RequestParam(required = false) String rejectionReason) {

        return prescriptionService.updateStatus(id, status, rejectionReason);
    }

    @GetMapping("/patient/{patientId}")
    public List<PrescriptionResponseDTO> getByPatientId(@PathVariable Long patientId) {
        return prescriptionService.getByPatientId(patientId);
    }
}
