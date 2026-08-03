package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.MedicineRequestDTO;
import com.arogyamed.healthcare.dto.MedicineResponseDTO;
import com.arogyamed.healthcare.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public MedicineResponseDTO createMedicine(@Valid @RequestBody MedicineRequestDTO request) {

        return medicineService.createMedicine(request);
    }

    @GetMapping("/{id}")
    public MedicineResponseDTO getMedicineById(@PathVariable Long id) {

        return medicineService.getMedicineById(id);
    }

    @PutMapping("/{id}")
    public MedicineResponseDTO updateMedicine(@PathVariable Long id, @Valid @RequestBody MedicineRequestDTO request) {

        return medicineService.updateMedicine(id, request);
    }

    @GetMapping
    public List<MedicineResponseDTO> getAllMedicines() {

        return medicineService.getAllMedicines();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<MedicineResponseDTO>> searchByMedicineName(@RequestParam String medicineName) {

        return ResponseEntity.ok(medicineService.searchByMedicineName(medicineName));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<MedicineResponseDTO>> searchByCategory(@RequestParam String category) {

        return ResponseEntity.ok(medicineService.searchByCategory(category));
    }

    @GetMapping("/search/company")
    public ResponseEntity<List<MedicineResponseDTO>> searchByCompany(@RequestParam String companyName) {

        return ResponseEntity.ok(medicineService.searchByCompany(companyName));
    }

    @GetMapping("/search/batch")
    public ResponseEntity<List<MedicineResponseDTO>> searchByBatchNumber(@RequestParam String batchNumber) {

        return ResponseEntity.ok(medicineService.searchByBatchNumber(batchNumber));
    }

    @GetMapping("/search/price")
    public ResponseEntity<List<MedicineResponseDTO>> searchByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {

        return ResponseEntity.ok(medicineService.searchByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/search/expiry-before")
    public ResponseEntity<List<MedicineResponseDTO>> searchByExpiryDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate) {

        return ResponseEntity.ok(medicineService.searchByExpiryDate(expiryDate));
    }

    @GetMapping("/search/low-stock")
    public ResponseEntity<List<MedicineResponseDTO>> searchLowStockMedicines(@RequestParam Integer stockQuantity) {

        return ResponseEntity.ok(medicineService.searchLowStockMedicines(stockQuantity));
    }

    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public MedicineResponseDTO uploadMedicineImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) {

        return medicineService.uploadMedicineImage(id, file);
    }
}
