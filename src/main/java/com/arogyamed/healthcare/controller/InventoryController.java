package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.InventoryRequestDTO;
import com.arogyamed.healthcare.dto.InventoryResponseDTO;
import com.arogyamed.healthcare.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public InventoryResponseDTO createInventory(@RequestBody InventoryRequestDTO request) {
        return inventoryService.createInventory(request);
    }

    @GetMapping("/{id}")
    public InventoryResponseDTO getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @PutMapping("/{id}")
    public InventoryResponseDTO updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDTO request) {
        return inventoryService.updateInventory(id, request);
    }

    @GetMapping
    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryService.getAllInventories();
    }
}
