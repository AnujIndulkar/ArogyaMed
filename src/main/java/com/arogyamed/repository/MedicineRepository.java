package com.arogyamed.repository;

import com.arogyamed.model.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Used by the symptom checker: medicines containing an ingredient, only in-stock ones
    List<Medicine> findByGenericNameContainingIgnoreCaseAndStockQuantityGreaterThan(String genericName, Integer stockQuantity);

    // Quality inspector: batches that have a company and batch number but no inspection yet
    @Query("""
            select m from Medicine m
            where m.company is not null
              and m.batchNumber is not null
              and lower(m.medicineName) like lower(concat('%', :q, '%'))
              and not exists (
                  select 1 from QualityCheck qc
                  where qc.medicine = m and qc.batchNumber = m.batchNumber
              )
            order by m.medicineName
            """)
    Page<Medicine> findPendingInspection(@Param("q") String q, Pageable pageable);
}