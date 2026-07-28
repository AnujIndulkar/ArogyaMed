package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.QualityCheckRequestDTO;
import com.arogyamed.healthcare.dto.QualityCheckResponseDTO;
import com.arogyamed.healthcare.model.QualityStatus;

import java.time.LocalDate;

import java.util.List;

public interface QualityCheckService {

    QualityCheckResponseDTO createQualityCheck(QualityCheckRequestDTO requestDTO);

    QualityCheckResponseDTO getQualityCheckById(Long id);

    List<QualityCheckResponseDTO> getAllQualityChecks();

    QualityCheckResponseDTO updateQualityCheck(Long id, QualityCheckRequestDTO requestDTO);

    void deleteQualityCheck(Long id);

    // ================= Search =================

    // Search by Medicine
    List<QualityCheckResponseDTO> searchByMedicine(Long medicineId);

    // Search by Company
    List<QualityCheckResponseDTO> searchByCompany(Long companyId);

    // Search by Inspector
    List<QualityCheckResponseDTO> searchByInspector(Long adminId);

    // Search by Quality Status
    List<QualityCheckResponseDTO> searchByQualityStatus(QualityStatus qualityStatus);

    // Search by Batch Number
    List<QualityCheckResponseDTO> searchByBatchNumber(String batchNumber);

    // Search by Inspection Date
    List<QualityCheckResponseDTO> searchByInspectionDate(LocalDate inspectionDate);

    // Search by Inspection Date Range
    List<QualityCheckResponseDTO> searchByInspectionDate(LocalDate startDate, LocalDate endDate);

    // Search by Packaging Verification
    List<QualityCheckResponseDTO> searchByPackagingVerified(boolean packagingVerified);

    // Search by Seal Verification
    List<QualityCheckResponseDTO> searchBySealVerified(boolean sealVerified);

    // Search by Temperature Verification
    List<QualityCheckResponseDTO> searchByTemperatureVerified(boolean temperatureVerified);

    // Search by Expiry Verification
    List<QualityCheckResponseDTO> searchByExpiryVerified(boolean expiryVerified);

}
