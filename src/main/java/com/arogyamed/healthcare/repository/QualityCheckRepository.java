package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Company;
import com.arogyamed.healthcare.model.Medicine;
import com.arogyamed.healthcare.model.QualityCheck;
import com.arogyamed.healthcare.model.QualityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualityCheckRepository extends JpaRepository<QualityCheck, Long> {

    List<QualityCheck> findByCompany(Company company);

    List<QualityCheck> findByMedicine(Medicine medicine);

    List<QualityCheck> findByQualityStatus(QualityStatus qualityStatus);

    List<QualityCheck> findByBatchNumber(String batchNumber);

}
