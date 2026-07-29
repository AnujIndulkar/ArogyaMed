package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.BarcodeQRCode;
import com.arogyamed.healthcare.model.BarcodeType;
import com.arogyamed.healthcare.model.Medicine;
import com.arogyamed.healthcare.model.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BarcodeQRCodeRepository extends JpaRepository<BarcodeQRCode, Long> {

    Optional<BarcodeQRCode> findByBarcode(String barcode);

    Optional<BarcodeQRCode> findByQrCode(String qrCode);

    Optional<BarcodeQRCode> findByMedicine(Medicine medicine);

    List<BarcodeQRCode> findByBarcodeType(BarcodeType barcodeType);

    List<BarcodeQRCode> findByVerificationStatus(VerificationStatus verificationStatus);

    long countByVerificationStatus(VerificationStatus verificationStatus);

    long countByBarcodeType(BarcodeType barcodeType);

    // =========================
    // MEDICINE / COMPANY SEARCH
    // =========================

    List<BarcodeQRCode> findByMedicine_MedicineNameContainingIgnoreCase(String medicineName);

    List<BarcodeQRCode> findByMedicine_GenericNameContainingIgnoreCase(String genericName);

    List<BarcodeQRCode> findByMedicine_Company_CompanyNameContainingIgnoreCase(String companyName);

    List<BarcodeQRCode> findByMedicine_BatchNumberContainingIgnoreCase(String batchNumber);

    // =========================
    // CODE SEARCH
    // =========================

    List<BarcodeQRCode> findByBarcodeContainingIgnoreCase(String barcode);

    List<BarcodeQRCode> findByQrCodeContainingIgnoreCase(String qrCode);

    // =========================
    // DATE SEARCH
    // =========================

    List<BarcodeQRCode> findByMedicine_ManufacturingDateBetween(LocalDate startDate, LocalDate endDate);

    List<BarcodeQRCode> findByMedicine_ExpiryDateBetween(LocalDate startDate, LocalDate endDate);

    List<BarcodeQRCode> findByMedicine_ExpiryDateBefore(LocalDate date);

    // =========================
    // SCAN COUNT SEARCH
    // =========================

    List<BarcodeQRCode> findByTotalScansGreaterThanEqual(Integer minScans);

    List<BarcodeQRCode> findByTotalScansBetween(Integer minScans, Integer maxScans);

    // =========================
    // COUNTERFEIT / ACTIVE SEARCH
    // =========================

    List<BarcodeQRCode> findByCounterfeitDetectedTrue();

    List<BarcodeQRCode> findByActiveTrue();

    List<BarcodeQRCode> findByActiveFalse();

    // =========================
    // DYNAMIC COMBINED FILTER
    // =========================

    @Query("""
            SELECT b FROM BarcodeQRCode b
            WHERE (:verificationStatus IS NULL OR b.verificationStatus = :verificationStatus)
            AND (:barcodeType IS NULL OR b.barcodeType = :barcodeType)
            AND (:active IS NULL OR b.active = :active)
            AND (:counterfeitDetected IS NULL OR b.counterfeitDetected = :counterfeitDetected)
            AND (:medicineName IS NULL OR LOWER(b.medicine.medicineName) LIKE LOWER(CONCAT('%', :medicineName, '%')))
            AND (:companyName IS NULL OR LOWER(b.medicine.company.companyName) LIKE LOWER(CONCAT('%', :companyName, '%')))
            """)
    List<BarcodeQRCode> searchBarcodes(
            @Param("verificationStatus") VerificationStatus verificationStatus,
            @Param("barcodeType") BarcodeType barcodeType,
            @Param("active") Boolean active,
            @Param("counterfeitDetected") Boolean counterfeitDetected,
            @Param("medicineName") String medicineName,
            @Param("companyName") String companyName
    );
}