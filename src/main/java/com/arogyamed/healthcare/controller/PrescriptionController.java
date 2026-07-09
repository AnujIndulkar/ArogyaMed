package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.PrescriptionRequestDTO;
import com.arogyamed.healthcare.dto.PrescriptionResponseDTO;
import com.arogyamed.healthcare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
