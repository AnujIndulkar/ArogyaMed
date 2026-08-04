package com.arogyamed.controller;

import com.arogyamed.dto.InventoryRequestDTO;
import com.arogyamed.dto.InventoryResponseDTO;
import com.arogyamed.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public InventoryResponseDTO createInventory(@Valid @RequestBody InventoryRequestDTO request) {
        return inventoryService.createInventory(request);
    }

    @GetMapping("/{id}")
    public InventoryResponseDTO getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @PutMapping("/{id}")
    public InventoryResponseDTO updateInventory(@PathVariable Long id, @Valid @RequestBody InventoryRequestDTO request) {
        return inventoryService.updateInventory(id, request);
    }

    @GetMapping
    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    // ================= Search =================

    // Search by Medicine Name
    @GetMapping("/search/medicine")
    public List<InventoryResponseDTO> searchByMedicineName(@RequestParam String medicineName) {
        return inventoryService.searchByMedicineName(medicineName);
    }

    // Search by Company Name
    @GetMapping("/search/company")
    public List<InventoryResponseDTO> searchByCompanyName(@RequestParam String companyName) {
        return inventoryService.searchByCompanyName(companyName);
    }

    // Search by Category
    @GetMapping("/search/category")
    public List<InventoryResponseDTO> searchByCategory(@RequestParam String category) {
        return inventoryService.searchByCategory(category);
    }

    // Search by Batch Number
    @GetMapping("/search/batch")
    public List<InventoryResponseDTO> searchByBatchNumber(@RequestParam String batchNumber) {
        return inventoryService.searchByBatchNumber(batchNumber);
    }

    // Search by Available Quantity
    @GetMapping("/search/quantity")
    public List<InventoryResponseDTO> searchByQuantity(@RequestParam Integer quantity) {
        return inventoryService.searchByQuantity(quantity);
    }

    // Search Low Stock
    @GetMapping("/search/low-stock")
    public List<InventoryResponseDTO> searchLowStock(@RequestParam Integer quantity) {
        return inventoryService.searchLowStock(quantity);
    }

    // Search by Last Updated
    @GetMapping("/search/last-updated")
    public List<InventoryResponseDTO> searchByLastUpdated(@RequestParam LocalDateTime lastUpdated) {
        return inventoryService.searchByLastUpdated(lastUpdated);
    }
}
