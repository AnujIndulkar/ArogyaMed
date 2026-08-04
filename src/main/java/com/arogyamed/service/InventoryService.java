package com.arogyamed.service;

import com.arogyamed.dto.InventoryRequestDTO;
import com.arogyamed.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    InventoryResponseDTO createInventory(InventoryRequestDTO request);

    InventoryResponseDTO getInventoryById(Long id);

    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO request);

    List<InventoryResponseDTO> getAllInventories();

    // ================= Search =================

    List<InventoryResponseDTO> searchByMedicineName(String medicineName);

    List<InventoryResponseDTO> searchByCompanyName(String companyName);

    List<InventoryResponseDTO> searchByCategory(String category);

    List<InventoryResponseDTO> searchByBatchNumber(String batchNumber);

    List<InventoryResponseDTO> searchByQuantity(Integer quantity);

    List<InventoryResponseDTO> searchLowStock(Integer quantity);

    List<InventoryResponseDTO> searchByLastUpdated(java.time.LocalDateTime lastUpdated);
}
