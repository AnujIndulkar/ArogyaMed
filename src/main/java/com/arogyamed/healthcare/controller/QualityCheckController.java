package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.QualityCheckRequestDTO;
import com.arogyamed.healthcare.dto.QualityCheckResponseDTO;
import com.arogyamed.healthcare.service.QualityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quality-checks")
@RequiredArgsConstructor
public class QualityCheckController {

    private final QualityCheckService qualityCheckService;

    @PostMapping
    public ResponseEntity<QualityCheckResponseDTO> createQualityCheck(
            @RequestBody QualityCheckRequestDTO requestDTO) {

        QualityCheckResponseDTO response =
                qualityCheckService.createQualityCheck(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QualityCheckResponseDTO> getQualityCheckById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                qualityCheckService.getQualityCheckById(id));
    }

    @GetMapping
    public ResponseEntity<List<QualityCheckResponseDTO>> getAllQualityChecks() {

        return ResponseEntity.ok(
                qualityCheckService.getAllQualityChecks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<QualityCheckResponseDTO> updateQualityCheck(
            @PathVariable Long id,
            @RequestBody QualityCheckRequestDTO requestDTO) {

        return ResponseEntity.ok(
                qualityCheckService.updateQualityCheck(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQualityCheck(
            @PathVariable Long id) {

        qualityCheckService.deleteQualityCheck(id);

        return ResponseEntity.ok("Quality Check deleted successfully.");
    }

}
