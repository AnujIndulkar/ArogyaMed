package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.AmbulanceRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceResponseDTO;
import com.arogyamed.healthcare.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.healthcare.model.AmbulanceStatus;
import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/ambulances")
public class AmbulanceController {

    @Autowired
    private AmbulanceService ambulanceService;

    @PostMapping
    public AmbulanceResponseDTO createAmbulance(@RequestBody AmbulanceRequestDTO request) {
        return ambulanceService.createAmbulance(request);
    }

    @GetMapping("/{id}")
    public AmbulanceResponseDTO getAmbulanceById(@PathVariable Long id) {
        return ambulanceService.getAmbulanceById(id);
    }

    @PutMapping("/{id}")
    public AmbulanceResponseDTO updateAmbulance(@PathVariable Long id, @RequestBody AmbulanceRequestDTO request) {
        return ambulanceService.updateAmbulance(id, request);
    }

    @GetMapping
    public List<AmbulanceResponseDTO> getAllAmbulances() {
        return ambulanceService.getAllAmbulances();
    }

    // ================= Search =================

    // Search by Ambulance Number
    @GetMapping("/search/ambulance-number")
    public List<AmbulanceResponseDTO> searchByAmbulanceNumber(@RequestParam String ambulanceNumber) {
        return ambulanceService.searchByAmbulanceNumber(ambulanceNumber);
    }

    // Search by Driver Name
    @GetMapping("/search/driver-name")
    public List<AmbulanceResponseDTO> searchByDriverName(@RequestParam String driverName) {
        return ambulanceService.searchByDriverName(driverName);
    }

    // Search by Driver Phone
    @GetMapping("/search/driver-phone")
    public List<AmbulanceResponseDTO> searchByDriverPhone(@RequestParam String driverPhone) {
        return ambulanceService.searchByDriverPhone(driverPhone);
    }

    // Search by Current Location
    @GetMapping("/search/location")
    public List<AmbulanceResponseDTO> searchByCurrentLocation(@RequestParam String currentLocation) {
        return ambulanceService.searchByCurrentLocation(currentLocation);
    }

    // Search by Status
    @GetMapping("/search/status")
    public List<AmbulanceResponseDTO> searchByStatus(@RequestParam AmbulanceStatus status) {
        return ambulanceService.searchByStatus(status);
    }

    // Search by Availability
    @GetMapping("/search/available")
    public List<AmbulanceResponseDTO> searchByAvailability(@RequestParam Boolean available) {
        return ambulanceService.searchByAvailability(available);
    }

    // Search by Registration Number
    @GetMapping("/search/registration-number")
    public List<AmbulanceResponseDTO> searchByRegistrationNumber(@RequestParam String registrationNumber) {
        return ambulanceService.searchByRegistrationNumber(registrationNumber);
    }

    // Search by Verification Status
    @GetMapping("/search/verified")
    public List<AmbulanceResponseDTO> searchByVerified(@RequestParam Boolean verified) {
        return ambulanceService.searchByVerified(verified);
    }

    // Search by Insurance Expiry
    @GetMapping("/search/insurance-expiry")
    public List<AmbulanceResponseDTO> searchByInsuranceExpiry(@RequestParam LocalDate date) {
        return ambulanceService.searchByInsuranceExpiry(date);
    }

    // Search by Fitness Certificate Expiry
    @GetMapping("/search/fitness-expiry")
    public List<AmbulanceResponseDTO> searchByFitnessCertificateExpiry(@RequestParam LocalDate date) {
        return ambulanceService.searchByFitnessCertificateExpiry(date);
    }

    // Search by Pollution Certificate Expiry
    @GetMapping("/search/pollution-expiry")
    public List<AmbulanceResponseDTO> searchByPollutionCertificateExpiry(@RequestParam LocalDate date) {
        return ambulanceService.searchByPollutionCertificateExpiry(date);
    }
}
