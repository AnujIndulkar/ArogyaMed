package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Search by Medicine Name
    List<Medicine> findByMedicineNameContainingIgnoreCase(String medicineName);

    // Search by Category
    List<Medicine> findByCategoryContainingIgnoreCase(String category);

    // Search by Company Name
    List<Medicine> findByCompany_CompanyNameContainingIgnoreCase(String companyName);

    // Search by Batch Number
    List<Medicine> findByBatchNumberContainingIgnoreCase(String batchNumber);

    // Search by Price Range
    List<Medicine> findByPriceBetween(Double minPrice, Double maxPrice);

    // Search Medicines Expiring Before a Date
    List<Medicine> findByExpiryDateBefore(LocalDate expiryDate);

    // Search Low Stock Medicines
    List<Medicine> findByStockQuantityLessThanEqual(Integer stockQuantity);
}
