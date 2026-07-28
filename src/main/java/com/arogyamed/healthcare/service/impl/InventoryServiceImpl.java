package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.InventoryRequestDTO;
import com.arogyamed.healthcare.dto.InventoryResponseDTO;
import com.arogyamed.healthcare.model.Inventory;
import com.arogyamed.healthcare.model.Medicine;
import com.arogyamed.healthcare.repository.InventoryRepository;
import com.arogyamed.healthcare.repository.MedicineRepository;
import com.arogyamed.healthcare.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO request) {

        Medicine medicine = medicineRepository.findById(request.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        Inventory inventory = new Inventory();

        inventory.setMedicine(medicine);
        inventory.setQuantityAvailable(request.getQuantityAvailable());
        inventory.setMinimumStockLevel(request.getMinimumStockLevel());
        inventory.setLastUpdated(LocalDateTime.now());

        return mapToDTO(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDTO getInventoryById(Long id) {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Inventory not found"));

        return mapToDTO(inventory);
    }

    @Override
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO request) {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Inventory not found"));

        inventory.setQuantityAvailable(request.getQuantityAvailable());

        inventory.setMinimumStockLevel(request.getMinimumStockLevel());

        inventory.setLastUpdated(LocalDateTime.now());

        return mapToDTO(inventoryRepository.save(inventory));
    }

    @Override
    public List<InventoryResponseDTO> getAllInventories() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private InventoryResponseDTO mapToDTO(Inventory inventory) {

        InventoryResponseDTO dto = new InventoryResponseDTO();

        dto.setId(inventory.getId());

        dto.setMedicineId(inventory.getMedicine().getId());

        dto.setMedicineName(inventory.getMedicine().getMedicineName());

        dto.setQuantityAvailable(inventory.getQuantityAvailable());

        dto.setMinimumStockLevel(inventory.getMinimumStockLevel());

        dto.setLastUpdated(inventory.getLastUpdated());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<InventoryResponseDTO> searchByMedicineName(String medicineName) {

        return mapToDTOList(inventoryRepository.findByMedicine_MedicineNameContainingIgnoreCase(medicineName));
    }

    @Override
    public List<InventoryResponseDTO> searchByCompanyName(String companyName) {

        return mapToDTOList(inventoryRepository.findByMedicine_Company_CompanyNameContainingIgnoreCase(companyName));
    }

    @Override
    public List<InventoryResponseDTO> searchByCategory(String category) {

        return mapToDTOList(inventoryRepository.findByMedicine_CategoryContainingIgnoreCase(category));
    }

    @Override
    public List<InventoryResponseDTO> searchByBatchNumber(String batchNumber) {

        return mapToDTOList(inventoryRepository.findByMedicine_BatchNumberContainingIgnoreCase(batchNumber));
    }

    @Override
    public List<InventoryResponseDTO> searchByQuantity(Integer quantity) {

        return mapToDTOList(inventoryRepository.findByQuantityAvailableGreaterThanEqual(quantity));
    }

    @Override
    public List<InventoryResponseDTO> searchLowStock(Integer quantity) {

        return mapToDTOList(inventoryRepository.findByQuantityAvailableLessThanEqual(quantity));
    }

    @Override
    public List<InventoryResponseDTO> searchByLastUpdated(LocalDateTime lastUpdated) {

        return mapToDTOList(inventoryRepository.findByLastUpdatedAfter(lastUpdated));
    }

    private List<InventoryResponseDTO> mapToDTOList(List<Inventory> inventories) {

        return inventories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
