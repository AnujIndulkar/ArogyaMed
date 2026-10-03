package com.arogyamed.repository;

import com.arogyamed.model.Company;
import com.arogyamed.model.Medicine;
import com.arogyamed.model.QualityCheck;
import com.arogyamed.model.QualityInspector;
import com.arogyamed.model.QualityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface QualityCheckRepository extends JpaRepository<QualityCheck, Long> {

    List<QualityCheck> findByCompany(Company company);

    List<QualityCheck> findByMedicine(Medicine medicine);

    List<QualityCheck> findByQualityStatus(QualityStatus qualityStatus);

    List<QualityCheck> findByBatchNumber(String batchNumber);

    long countByQualityStatus(QualityStatus qualityStatus);

    // ================= Search =================

    List<QualityCheck> findByInspector(QualityInspector inspector);

    List<QualityCheck> findByInspectionDate(LocalDate inspectionDate);

    List<QualityCheck> findByInspectionDateBetween(LocalDate startDate, LocalDate endDate);

    List<QualityCheck> findByPackagingVerified(boolean packagingVerified);

    List<QualityCheck> findBySealVerified(boolean sealVerified);

    List<QualityCheck> findByTemperatureVerified(boolean temperatureVerified);

    List<QualityCheck> findByExpiryVerified(boolean expiryVerified);

}