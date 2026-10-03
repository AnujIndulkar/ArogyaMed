package com.arogyamed.controller;

import com.arogyamed.dto.AmbulanceRequestDTO;
import com.arogyamed.dto.AmbulanceResponseDTO;
import com.arogyamed.model.AmbulanceStatus;
import com.arogyamed.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<AmbulanceResponseDTO> getAllAmbulances() {
        return ambulanceService.getAllAmbulances();
    }

    @GetMapping("/{id}")
    public AmbulanceResponseDTO getAmbulanceById(@PathVariable Long id) {
        return ambulanceService.getAmbulanceById(id);
    }

    // Used by the ambulance provider dashboard right after login
    @GetMapping("/user/{userId}")
    public AmbulanceResponseDTO getAmbulanceByUserId(@PathVariable Long userId) {
        return ambulanceService.getAmbulanceByUserId(userId);
    }

    @PutMapping("/{id}")
    public AmbulanceResponseDTO updateAmbulance(@PathVariable Long id, @RequestBody AmbulanceRequestDTO request) {
        return ambulanceService.updateAmbulance(id, request);
    }

    // Driver toggles Available / On Duty / Maintenance from their dashboard
    @PatchMapping("/{id}/status")
    public AmbulanceResponseDTO updateAmbulanceStatus(
            @PathVariable Long id,
            @RequestParam AmbulanceStatus status,
            @RequestParam boolean available) {
        return ambulanceService.updateAmbulanceStatus(id, status, available);
    }

    // ================= Search =================

    @GetMapping("/search/ambulance-number")
    public List<AmbulanceResponseDTO> searchByAmbulanceNumber(@RequestParam String ambulanceNumber) {
        return ambulanceService.searchByAmbulanceNumber(ambulanceNumber);
    }

    @GetMapping("/search/driver-name")
    public List<AmbulanceResponseDTO> searchByDriverName(@RequestParam String driverName) {
        return ambulanceService.searchByDriverName(driverName);
    }

    @GetMapping("/search/driver-phone")
    public List<AmbulanceResponseDTO> searchByDriverPhone(@RequestParam String driverPhone) {
        return ambulanceService.searchByDriverPhone(driverPhone);
    }

    @GetMapping("/search/current-location")
    public List<AmbulanceResponseDTO> searchByCurrentLocation(@RequestParam String currentLocation) {
        return ambulanceService.searchByCurrentLocation(currentLocation);
    }

    @GetMapping("/search/status")
    public List<AmbulanceResponseDTO> searchByStatus(@RequestParam AmbulanceStatus status) {
        return ambulanceService.searchByStatus(status);
    }

    @GetMapping("/search/availability")
    public List<AmbulanceResponseDTO> searchByAvailability(@RequestParam Boolean available) {
        return ambulanceService.searchByAvailability(available);
    }

    @GetMapping("/search/registration-number")
    public List<AmbulanceResponseDTO> searchByRegistrationNumber(@RequestParam String registrationNumber) {
        return ambulanceService.searchByRegistrationNumber(registrationNumber);
    }

    @GetMapping("/search/verified")
    public List<AmbulanceResponseDTO> searchByVerified(@RequestParam Boolean verified) {
        return ambulanceService.searchByVerified(verified);
    }

    @GetMapping("/search/insurance-expiry")
    public List<AmbulanceResponseDTO> searchByInsuranceExpiry(@RequestParam LocalDate date) {
        return ambulanceService.searchByInsuranceExpiry(date);
    }

    @GetMapping("/search/fitness-certificate-expiry")
    public List<AmbulanceResponseDTO> searchByFitnessCertificateExpiry(@RequestParam LocalDate date) {
        return ambulanceService.searchByFitnessCertificateExpiry(date);
    }

    @GetMapping("/search/pollution-certificate-expiry")
    public List<AmbulanceResponseDTO> searchByPollutionCertificateExpiry(@RequestParam LocalDate date) {
        return ambulanceService.searchByPollutionCertificateExpiry(date);
    }
}
