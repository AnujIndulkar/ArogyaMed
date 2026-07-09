package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.MedicalRecordRequestDTO;
import com.arogyamed.healthcare.dto.MedicalRecordResponseDTO;
import com.arogyamed.healthcare.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
