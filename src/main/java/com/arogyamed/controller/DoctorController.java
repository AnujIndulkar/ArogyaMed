package com.arogyamed.controller;

import com.arogyamed.dto.DoctorRequestDTO;
import com.arogyamed.dto.DoctorResponseDTO;
import com.arogyamed.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public DoctorResponseDTO createDoctor(@Valid @RequestBody DoctorRequestDTO request) {
        return doctorService.createDoctor(request);
    }

    @GetMapping("/{userId}")
    public DoctorResponseDTO getDoctor(@PathVariable Long userId) {
        return doctorService.getDoctorByUserId(userId);
    }

    @PutMapping("/{userId}")
    public DoctorResponseDTO updateDoctor(@PathVariable Long userId, @Valid @RequestBody DoctorRequestDTO request) {
        return doctorService.updateDoctor(userId, request);
    }

    // ================= Search APIs =================

    // Search by Doctor Name
    @GetMapping("/search/name")
    public List<DoctorResponseDTO> searchByDoctorName(@RequestParam String fullName) {

        return doctorService.searchByDoctorName(fullName);
    }

    // Search by Specialization
    @GetMapping("/search/specialization")
    public List<DoctorResponseDTO> searchBySpecialization(@RequestParam String specialization) {

        return doctorService.searchBySpecialization(specialization);
    }

    // Search by Qualification
    @GetMapping("/search/qualification")
    public List<DoctorResponseDTO> searchByQualification(@RequestParam String qualification) {

        return doctorService.searchByQualification(qualification);
    }

    // Search by Experience
    @GetMapping("/search/experience")
    public List<DoctorResponseDTO> searchByExperience(@RequestParam Integer experienceYears) {

        return doctorService.searchByExperience(experienceYears);
    }

    // Search by Hospital
    @GetMapping("/search/hospital")
    public List<DoctorResponseDTO> searchByHospital(@RequestParam String hospitalName) {

        return doctorService.searchByHospital(hospitalName);
    }

    // Search by Consultation Fee
    @GetMapping("/search/consultation-fee")
    public List<DoctorResponseDTO> searchByConsultationFee(@RequestParam Double minFee, @RequestParam Double maxFee) {

        return doctorService.searchByConsultationFee(minFee, maxFee);
    }

    // Search by License Number
    @GetMapping("/search/license-number")
    public List<DoctorResponseDTO> searchByLicenseNumber(@RequestParam String licenseNumber) {

        return doctorService.searchByLicenseNumber(licenseNumber);
    }
}
