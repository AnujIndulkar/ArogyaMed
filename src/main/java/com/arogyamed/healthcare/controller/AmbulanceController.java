package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.AmbulanceRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceResponseDTO;
import com.arogyamed.healthcare.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
