package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Dashboard Counts
    long countByQuantityAvailableGreaterThan(Integer quantity);

    long countByQuantityAvailableBetween(Integer min, Integer max);

    long countByQuantityAvailable(Integer quantity);

    // ================= Search =================

    // Search by Medicine Name
    List<Inventory> findByMedicine_MedicineNameContainingIgnoreCase(String medicineName);

    // Search by Company Name
    List<Inventory> findByMedicine_Company_CompanyNameContainingIgnoreCase(String companyName);

    // Search by Category
    List<Inventory> findByMedicine_CategoryContainingIgnoreCase(String category);

    // Search by Batch Number
    List<Inventory> findByMedicine_BatchNumberContainingIgnoreCase(String batchNumber);

    // Search by Quantity Available
    List<Inventory> findByQuantityAvailableGreaterThanEqual(Integer quantity);

    // Search by Low Stock
    List<Inventory> findByQuantityAvailableLessThanEqual(Integer quantity);

    // Search by Last Updated
    List<Inventory> findByLastUpdatedAfter(java.time.LocalDateTime lastUpdated);
}
