package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.MedicineRequestDTO;
import com.arogyamed.healthcare.dto.MedicineResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface MedicineService {

    MedicineResponseDTO createMedicine(MedicineRequestDTO request);

    MedicineResponseDTO getMedicineById(Long id);

    MedicineResponseDTO updateMedicine(Long id, MedicineRequestDTO request);

    List<MedicineResponseDTO> getAllMedicines();

    List<MedicineResponseDTO> searchByMedicineName(String medicineName);

    List<MedicineResponseDTO> searchByCategory(String category);

    List<MedicineResponseDTO> searchByCompany(String companyName);

    List<MedicineResponseDTO> searchByBatchNumber(String batchNumber);

    List<MedicineResponseDTO> searchByPriceRange(Double minPrice, Double maxPrice);

    List<MedicineResponseDTO> searchByExpiryDate(LocalDate expiryDate);

    List<MedicineResponseDTO> searchLowStockMedicines(Integer stockQuantity);
}
