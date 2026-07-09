package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.DoctorRequestDTO;
import com.arogyamed.healthcare.dto.DoctorResponseDTO;
import com.arogyamed.healthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public DoctorResponseDTO createDoctor(@RequestBody DoctorRequestDTO request) {
        return doctorService.createDoctor(request);
    }

    @GetMapping("/{userId}")
    public DoctorResponseDTO getDoctor(@PathVariable Long userId) {
        return doctorService.getDoctorByUserId(userId);
    }

    @PutMapping("/{userId}")
    public DoctorResponseDTO updateDoctor(@PathVariable Long userId, @RequestBody DoctorRequestDTO request) {
        return doctorService.updateDoctor(userId, request);
    }
}
