package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.BarcodeDashboardDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeRequestDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeResponseDTO;
import com.arogyamed.healthcare.model.BarcodeType;
import com.arogyamed.healthcare.model.VerificationStatus;

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

}
