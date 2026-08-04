package com.arogyamed.repository;

import com.arogyamed.model.Company;
import com.arogyamed.model.Medicine;
import com.arogyamed.model.QualityCheck;
import com.arogyamed.model.QualityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.arogyamed.model.Admin;

import java.time.LocalDate;

import java.util.List;

public interface QualityCheckRepository extends JpaRepository<QualityCheck, Long> {

    List<QualityCheck> findByCompany(Company company);

    List<QualityCheck> findByMedicine(Medicine medicine);

    List<QualityCheck> findByQualityStatus(QualityStatus qualityStatus);

    List<QualityCheck> findByBatchNumber(String batchNumber);

    long countByQualityStatus(QualityStatus qualityStatus);

    // ================= Search =================

    // Search by Inspector
    List<QualityCheck> findByInspector(Admin inspector);

    // Search by Inspection Date
    List<QualityCheck> findByInspectionDate(LocalDate inspectionDate);

    // Search by Inspection Date Range
    List<QualityCheck> findByInspectionDateBetween(LocalDate startDate, LocalDate endDate);

    // Search by Packaging Verification
    List<QualityCheck> findByPackagingVerified(boolean packagingVerified);

    // Search by Seal Verification
    List<QualityCheck> findBySealVerified(boolean sealVerified);

    // Search by Temperature Verification
    List<QualityCheck> findByTemperatureVerified(boolean temperatureVerified);

    // Search by Expiry Verification
    List<QualityCheck> findByExpiryVerified(boolean expiryVerified);

}
