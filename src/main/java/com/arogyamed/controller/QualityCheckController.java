package com.arogyamed.controller;

import com.arogyamed.dto.QualityCheckRequestDTO;
import com.arogyamed.dto.QualityCheckResponseDTO;
import com.arogyamed.service.QualityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.model.QualityStatus;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/quality-checks")
@RequiredArgsConstructor
public class QualityCheckController {

    private final QualityCheckService qualityCheckService;

    @PostMapping
    public ResponseEntity<QualityCheckResponseDTO> createQualityCheck(@RequestBody QualityCheckRequestDTO requestDTO) {

        QualityCheckResponseDTO response = qualityCheckService.createQualityCheck(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QualityCheckResponseDTO> getQualityCheckById(@PathVariable Long id) {

        return ResponseEntity.ok(qualityCheckService.getQualityCheckById(id));
    }

    @GetMapping
    public ResponseEntity<List<QualityCheckResponseDTO>> getAllQualityChecks() {

        return ResponseEntity.ok(qualityCheckService.getAllQualityChecks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<QualityCheckResponseDTO> updateQualityCheck(@PathVariable Long id, @RequestBody QualityCheckRequestDTO requestDTO) {

        return ResponseEntity.ok(qualityCheckService.updateQualityCheck(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQualityCheck(@PathVariable Long id) {

        qualityCheckService.deleteQualityCheck(id);

        return ResponseEntity.ok("Quality Check deleted successfully.");
    }

    // ================= Search =================

    @GetMapping("/search/medicine/{medicineId}")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByMedicine(@PathVariable Long medicineId) {

        return ResponseEntity.ok(qualityCheckService.searchByMedicine(medicineId));
    }

    @GetMapping("/search/company/{companyId}")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByCompany(@PathVariable Long companyId) {

        return ResponseEntity.ok(qualityCheckService.searchByCompany(companyId));
    }

    // inspectorId here = QualityInspector.id, obtained via GET /api/quality-inspectors/{userId}
    @GetMapping("/search/inspector/{inspectorId}")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByInspector(@PathVariable Long inspectorId) {

        return ResponseEntity.ok(qualityCheckService.searchByInspector(inspectorId));
    }

    @GetMapping("/search/status")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByQualityStatus(@RequestParam QualityStatus qualityStatus) {

        return ResponseEntity.ok(qualityCheckService.searchByQualityStatus(qualityStatus));
    }

    @GetMapping("/search/batch")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByBatchNumber(@RequestParam String batchNumber) {

        return ResponseEntity.ok(qualityCheckService.searchByBatchNumber(batchNumber));
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByInspectionDate(@RequestParam LocalDate inspectionDate) {

        return ResponseEntity.ok(qualityCheckService.searchByInspectionDate(inspectionDate));
    }

    @GetMapping("/search/date-range")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByInspectionDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        return ResponseEntity.ok(qualityCheckService.searchByInspectionDate(startDate, endDate));
    }

    @GetMapping("/search/packaging")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByPackagingVerified(@RequestParam boolean packagingVerified) {

        return ResponseEntity.ok(qualityCheckService.searchByPackagingVerified(packagingVerified));
    }

    @GetMapping("/search/seal")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchBySealVerified(@RequestParam boolean sealVerified) {

        return ResponseEntity.ok(qualityCheckService.searchBySealVerified(sealVerified));
    }

    @GetMapping("/search/temperature")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByTemperatureVerified(@RequestParam boolean temperatureVerified) {

        return ResponseEntity.ok(qualityCheckService.searchByTemperatureVerified(temperatureVerified));
    }

    @GetMapping("/search/expiry")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByExpiryVerified(@RequestParam boolean expiryVerified) {

        return ResponseEntity.ok(qualityCheckService.searchByExpiryVerified(expiryVerified));
    }

}