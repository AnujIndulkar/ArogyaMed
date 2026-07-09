package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.InventoryRequestDTO;
import com.arogyamed.healthcare.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    InventoryResponseDTO createInventory(InventoryRequestDTO request);

    InventoryResponseDTO getInventoryById(Long id);

    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO request);

    List<InventoryResponseDTO> getAllInventories();
}
