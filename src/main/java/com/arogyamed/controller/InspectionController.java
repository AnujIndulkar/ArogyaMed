package com.arogyamed.controller;

import com.arogyamed.dto.InspectionDTO;
import com.arogyamed.dto.QualityCheckResponseDTO;
import com.arogyamed.service.InspectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final InspectionService inspectionService;

    // batches nobody has inspected yet (optionally filtered by medicine name)
    @GetMapping("/pending")
    public List<InspectionDTO.PendingBatch> pending(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "50") int limit) {
        return inspectionService.pendingBatches(q, limit);
    }

    // inspections done by the logged-in inspector
    @GetMapping("/mine")
    public List<QualityCheckResponseDTO> mine(Authentication authentication) {
        return inspectionService.myInspections(authentication.getName());
    }

    @GetMapping("/summary")
    public InspectionDTO.Summary summary(Authentication authentication) {
        return inspectionService.summary(authentication.getName());
    }

    // approve or reject a batch
    @PostMapping("/submit")
    public QualityCheckResponseDTO submit(
            @Valid @RequestBody InspectionDTO.SubmitRequest request,
            Authentication authentication) {
        return inspectionService.submit(authentication.getName(), request);
    }
}