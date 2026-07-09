package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.SOSRequestDTO;
import com.arogyamed.healthcare.dto.SOSResponseDTO;
import com.arogyamed.healthcare.service.SOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sos")
public class SOSController {

    @Autowired
    private SOSService sosService;

    @PostMapping
    public SOSResponseDTO createSOS(@RequestBody SOSRequestDTO request) {
        return sosService.createSOS(request);
    }

    @GetMapping("/{id}")
    public SOSResponseDTO getSOSById(@PathVariable Long id) {
        return sosService.getSOSById(id);
    }

    @PutMapping("/{id}")
    public SOSResponseDTO updateSOS(@PathVariable Long id, @RequestBody SOSRequestDTO request) {
        return sosService.updateSOS(id, request);
    }

    @GetMapping
    public List<SOSResponseDTO> getAllSOS() {
        return sosService.getAllSOS();
    }
}
