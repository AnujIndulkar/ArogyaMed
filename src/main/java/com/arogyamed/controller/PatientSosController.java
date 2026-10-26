package com.arogyamed.controller;

import com.arogyamed.dto.PatientSosDTO;
import com.arogyamed.service.PatientSosService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sos/my")
public class PatientSosController {

    private final PatientSosService service;

    public PatientSosController(PatientSosService service) {
        this.service = service;
    }

    // the SOS button: creates an emergency for the logged-in patient
    @PostMapping
    public PatientSosDTO.Status create(@Valid @RequestBody PatientSosDTO.CreateRequest request,
                                       Authentication authentication) {
        return service.create(authentication.getName(), request);
    }

    // the patient's current emergency, or 204 if there is none
    @GetMapping("/active")
    public ResponseEntity<PatientSosDTO.Status> active(Authentication authentication) {
        return service.active(authentication.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // cancel a request that no ambulance has accepted yet (for example an accidental press)
    @PostMapping("/{id}/cancel")
    public PatientSosDTO.Status cancel(@PathVariable Long id, Authentication authentication) {
        return service.cancel(authentication.getName(), id);
    }
}