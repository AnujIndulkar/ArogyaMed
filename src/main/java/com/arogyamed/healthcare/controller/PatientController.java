package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.PatientRequestDTO;
import com.arogyamed.healthcare.dto.PatientResponseDTO;
import com.arogyamed.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public PatientResponseDTO create(@RequestBody PatientRequestDTO request) {
        return patientService.createPatient(request);
    }

    @GetMapping("/{userId}")
    public PatientResponseDTO get(@PathVariable Long userId) {
        return patientService.getPatientByUserId(userId);
    }

    @PutMapping("/{userId}")
    public PatientResponseDTO update(@PathVariable Long userId, @RequestBody PatientRequestDTO request) {
        return patientService.updatePatient(userId, request);
    }
}
