package com.arogyamed.service;

import com.arogyamed.dto.BarcodeDashboardDTO;
import com.arogyamed.dto.BarcodeQRCodeRequestDTO;
import com.arogyamed.dto.BarcodeQRCodeResponseDTO;
import com.arogyamed.model.BarcodeType;
import com.arogyamed.model.VerificationStatus;

import java.time.LocalDate;
import java.util.List;

public interface BarcodeQRCodeService {

    BarcodeQRCodeResponseDTO createBarcodeQRCode(BarcodeQRCodeRequestDTO requestDTO);

    BarcodeQRCodeResponseDTO getBarcodeQRCodeById(Long id);

    BarcodeQRCodeResponseDTO updateBarcodeQRCode(Long id, BarcodeQRCodeRequestDTO requestDTO);

    void deleteBarcodeQRCode(Long id);

    List<BarcodeQRCodeResponseDTO> getAllBarcodeQRCodes();

    BarcodeQRCodeResponseDTO getByBarcode(String barcode);

    BarcodeQRCodeResponseDTO getByQrCode(String qrCode);

    BarcodeQRCodeResponseDTO getByMedicineId(Long medicineId);

    List<BarcodeQRCodeResponseDTO> getByBarcodeType(BarcodeType barcodeType);

    List<BarcodeQRCodeResponseDTO> getByVerificationStatus(VerificationStatus verificationStatus);

    BarcodeQRCodeResponseDTO verifyBarcode(String barcode);

    BarcodeDashboardDTO getBarcodeDashboard();

    // ================= Verification / Status Actions =================

    BarcodeQRCodeResponseDTO flagAsCounterfeit(String barcode, String remarks);

    BarcodeQRCodeResponseDTO activate(Long id);

    BarcodeQRCodeResponseDTO deactivate(Long id);

    // ================= Enterprise Search & Filtering =================

    List<BarcodeQRCodeResponseDTO> searchByMedicineName(String medicineName);

    List<BarcodeQRCodeResponseDTO> searchByGenericName(String genericName);

    List<BarcodeQRCodeResponseDTO> searchByCompanyName(String companyName);

    List<BarcodeQRCodeResponseDTO> searchByBatchNumber(String batchNumber);

    List<BarcodeQRCodeResponseDTO> searchByBarcode(String barcode);

    List<BarcodeQRCodeResponseDTO> searchByQrCode(String qrCode);

    List<BarcodeQRCodeResponseDTO> searchByManufacturingDateRange(LocalDate startDate, LocalDate endDate);

    List<BarcodeQRCodeResponseDTO> searchByExpiryDateRange(LocalDate startDate, LocalDate endDate);

    List<BarcodeQRCodeResponseDTO> searchExpired();

    List<BarcodeQRCodeResponseDTO> searchByScanCountMin(Integer minScans);

    List<BarcodeQRCodeResponseDTO> searchByScanCountRange(Integer minScans, Integer maxScans);

    List<BarcodeQRCodeResponseDTO> searchCounterfeit();

    List<BarcodeQRCodeResponseDTO> searchActive();

    List<BarcodeQRCodeResponseDTO> searchInactive();

    List<BarcodeQRCodeResponseDTO> searchBarcodes(
            VerificationStatus verificationStatus,
            BarcodeType barcodeType,
            Boolean active,
            Boolean counterfeitDetected,
            String medicineName,
            String companyName
    );
}
