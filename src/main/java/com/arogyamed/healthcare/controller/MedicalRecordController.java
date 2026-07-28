package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.MedicalRecordRequestDTO;
import com.arogyamed.healthcare.dto.MedicalRecordResponseDTO;
import com.arogyamed.healthcare.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public MedicalRecordResponseDTO createMedicalRecord(@RequestBody MedicalRecordRequestDTO request) {
        return medicalRecordService.createMedicalRecord(request);
    }

    @GetMapping("/{id}")
    public MedicalRecordResponseDTO getMedicalRecordById(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @PutMapping("/{id}")
    public MedicalRecordResponseDTO updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordRequestDTO request) {
        return medicalRecordService.updateMedicalRecord(id, request);
    }

    @GetMapping
    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    // ================= Search =================

    // Search by Patient Name
    @GetMapping("/search/patient")
    public List<MedicalRecordResponseDTO> searchByPatientName(@RequestParam String fullName) {

        return medicalRecordService.searchByPatientName(fullName);
    }

    // Search by Diagnosis
    @GetMapping("/search/diagnosis")
    public List<MedicalRecordResponseDTO> searchByDiagnosis(@RequestParam String diagnosis) {

        return medicalRecordService.searchByDiagnosis(diagnosis);
    }

    // Search by Treatment
    @GetMapping("/search/treatment")
    public List<MedicalRecordResponseDTO> searchByTreatment(@RequestParam String treatment) {

        return medicalRecordService.searchByTreatment(treatment);
    }

    // Search by Doctor Notes
    @GetMapping("/search/doctor-notes")
    public List<MedicalRecordResponseDTO> searchByDoctorNotes(@RequestParam String doctorNotes) {

        return medicalRecordService.searchByDoctorNotes(doctorNotes);
    }

    // Search by Visit Date
    @GetMapping("/search/visit-date")
    public List<MedicalRecordResponseDTO> searchByVisitDate(@RequestParam LocalDate visitDate) {

        return medicalRecordService.searchByVisitDate(visitDate);
    }

    // Search by Visit Date Range
    @GetMapping("/search/date-range")
    public List<MedicalRecordResponseDTO> searchByVisitDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        return medicalRecordService.searchByVisitDateRange(startDate, endDate);
    }
}
