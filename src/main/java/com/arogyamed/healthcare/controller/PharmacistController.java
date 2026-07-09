package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.PharmacistRequestDTO;
import com.arogyamed.healthcare.dto.PharmacistResponseDTO;
import com.arogyamed.healthcare.service.PharmacistService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pharmacists")
public class PharmacistController {

    @Autowired
    private PharmacistService pharmacistService;

    @PostMapping
    public PharmacistResponseDTO createPharmacist(@RequestBody PharmacistRequestDTO request) {
        return pharmacistService.createPharmacist(request);
    }

    @GetMapping("/{userId}")
    public PharmacistResponseDTO getPharmacistByUserId(@PathVariable Long userId) {
        return pharmacistService.getPharmacistByUserId(userId);
    }

    @PutMapping("/{userId}")
    public PharmacistResponseDTO updatePharmacist(@PathVariable Long userId, @RequestBody PharmacistRequestDTO request) {
        return pharmacistService.updatePharmacist(userId, request);
    }
}
