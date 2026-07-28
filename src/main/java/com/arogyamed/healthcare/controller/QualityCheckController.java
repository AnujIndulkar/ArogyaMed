package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.QualityCheckRequestDTO;
import com.arogyamed.healthcare.dto.QualityCheckResponseDTO;
import com.arogyamed.healthcare.service.QualityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.healthcare.model.QualityStatus;

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

    // Search by Medicine
    @GetMapping("/search/medicine/{medicineId}")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByMedicine(@PathVariable Long medicineId) {

        return ResponseEntity.ok(qualityCheckService.searchByMedicine(medicineId));
    }

    // Search by Company
    @GetMapping("/search/company/{companyId}")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByCompany(@PathVariable Long companyId) {

        return ResponseEntity.ok(qualityCheckService.searchByCompany(companyId));
    }

    // Search by Inspector
    @GetMapping("/search/inspector/{adminId}")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByInspector(@PathVariable Long adminId) {

        return ResponseEntity.ok(qualityCheckService.searchByInspector(adminId));
    }

    // Search by Quality Status
    @GetMapping("/search/status")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByQualityStatus(@RequestParam QualityStatus qualityStatus) {

        return ResponseEntity.ok(qualityCheckService.searchByQualityStatus(qualityStatus));
    }

    // Search by Batch Number
    @GetMapping("/search/batch")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByBatchNumber(@RequestParam String batchNumber) {

        return ResponseEntity.ok(qualityCheckService.searchByBatchNumber(batchNumber));
    }

    // Search by Inspection Date
    @GetMapping("/search/date")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByInspectionDate(@RequestParam LocalDate inspectionDate) {

        return ResponseEntity.ok(qualityCheckService.searchByInspectionDate(inspectionDate));
    }

    // Search by Inspection Date Range
    @GetMapping("/search/date-range")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByInspectionDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        return ResponseEntity.ok(qualityCheckService.searchByInspectionDate(startDate, endDate));
    }

    // Search by Packaging Verification
    @GetMapping("/search/packaging")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByPackagingVerified(@RequestParam boolean packagingVerified) {

        return ResponseEntity.ok(qualityCheckService.searchByPackagingVerified(packagingVerified));
    }

    // Search by Seal Verification
    @GetMapping("/search/seal")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchBySealVerified(@RequestParam boolean sealVerified) {

        return ResponseEntity.ok(qualityCheckService.searchBySealVerified(sealVerified));
    }

    // Search by Temperature Verification
    @GetMapping("/search/temperature")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByTemperatureVerified(@RequestParam boolean temperatureVerified) {

        return ResponseEntity.ok(qualityCheckService.searchByTemperatureVerified(temperatureVerified));
    }

    // Search by Expiry Verification
    @GetMapping("/search/expiry")
    public ResponseEntity<List<QualityCheckResponseDTO>> searchByExpiryVerified(@RequestParam boolean expiryVerified) {

        return ResponseEntity.ok(qualityCheckService.searchByExpiryVerified(expiryVerified));
    }

}
