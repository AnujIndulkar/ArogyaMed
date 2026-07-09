package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.MedicineRequestDTO;
import com.arogyamed.healthcare.dto.MedicineResponseDTO;
import com.arogyamed.healthcare.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public MedicineResponseDTO createMedicine(@RequestBody MedicineRequestDTO request) {
        return medicineService.createMedicine(request);
    }

    @GetMapping("/{id}")
    public MedicineResponseDTO getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @PutMapping("/{id}")
    public MedicineResponseDTO updateMedicine(@PathVariable Long id, @RequestBody MedicineRequestDTO request) {
        return medicineService.updateMedicine(id, request);
    }

    @GetMapping
    public List<MedicineResponseDTO> getAllMedicines() {
        return medicineService.getAllMedicines();
    }
}
