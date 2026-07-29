package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.SOSRequestDTO;
import com.arogyamed.healthcare.dto.SOSResponseDTO;
import com.arogyamed.healthcare.service.SOSService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.healthcare.model.SOSStatus;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/sos")
public class SOSController {

    @Autowired
    private SOSService sosService;

    @PostMapping
    public SOSResponseDTO createSOS(@Valid @RequestBody SOSRequestDTO request) {
        return sosService.createSOS(request);
    }

    @GetMapping("/{id}")
    public SOSResponseDTO getSOSById(@PathVariable Long id) {
        return sosService.getSOSById(id);
    }

    @PutMapping("/{id}")
    public SOSResponseDTO updateSOS(@PathVariable Long id, @Valid @RequestBody SOSRequestDTO request)  {
        return sosService.updateSOS(id, request);
    }

    @GetMapping
    public List<SOSResponseDTO> getAllSOS() {
        return sosService.getAllSOS();
    }

    // ================= Search =================

    // Search by Patient Name
    @GetMapping("/search/patient")
    public List<SOSResponseDTO> searchByPatientName(@RequestParam String fullName) {

        return sosService.searchByPatientName(fullName);
    }

    // Search by Emergency Type
    @GetMapping("/search/emergency-type")
    public List<SOSResponseDTO> searchByEmergencyType(@RequestParam String emergencyType) {

        return sosService.searchByEmergencyType(emergencyType);
    }

    // Search by Location
    @GetMapping("/search/location")
    public List<SOSResponseDTO> searchByLocation(@RequestParam String location) {

        return sosService.searchByLocation(location);
    }

    // Search by Status
    @GetMapping("/search/status")
    public List<SOSResponseDTO> searchByStatus(@RequestParam SOSStatus status) {

        return sosService.searchByStatus(status);
    }

    // Search by Created Date Range
    @GetMapping("/search/date-range")
    public List<SOSResponseDTO> searchByCreatedDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {

        return sosService.searchByCreatedDateRange(startDate, endDate);
    }
}
