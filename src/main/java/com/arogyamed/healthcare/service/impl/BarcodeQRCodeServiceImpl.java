package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.BarcodeDashboardDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeRequestDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeResponseDTO;
import com.arogyamed.healthcare.model.*;
import com.arogyamed.healthcare.repository.BarcodeQRCodeRepository;
import com.arogyamed.healthcare.repository.MedicineRepository;
import com.arogyamed.healthcare.service.BarcodeQRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BarcodeQRCodeServiceImpl implements BarcodeQRCodeService {

    private final BarcodeQRCodeRepository barcodeQRCodeRepository;

    private final MedicineRepository medicineRepository;

    @Override
    public BarcodeQRCodeResponseDTO createBarcodeQRCode(BarcodeQRCodeRequestDTO requestDTO) {

        Medicine medicine = medicineRepository.findById(requestDTO.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        BarcodeQRCode barcodeQRCode = new BarcodeQRCode();

        barcodeQRCode.setMedicine(medicine);
        barcodeQRCode.setBarcode(requestDTO.getBarcode());
        barcodeQRCode.setQrCode(requestDTO.getQrCode());
        barcodeQRCode.setBarcodeType(requestDTO.getBarcodeType());
        barcodeQRCode.setVerificationStatus(requestDTO.getVerificationStatus());
        barcodeQRCode.setTotalScans(requestDTO.getTotalScans());
        barcodeQRCode.setRemarks(requestDTO.getRemarks());

        BarcodeQRCode savedBarcode = barcodeQRCodeRepository.save(barcodeQRCode);

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(savedBarcode.getId());
        responseDTO.setMedicineId(savedBarcode.getMedicine().getId());
        responseDTO.setMedicineName(savedBarcode.getMedicine().getMedicineName());
        responseDTO.setBarcode(savedBarcode.getBarcode());
        responseDTO.setQrCode(savedBarcode.getQrCode());
        responseDTO.setBarcodeType(savedBarcode.getBarcodeType());
        responseDTO.setVerificationStatus(savedBarcode.getVerificationStatus());
        responseDTO.setTotalScans(savedBarcode.getTotalScans());
        responseDTO.setLastScannedAt(savedBarcode.getLastScannedAt());
        responseDTO.setRemarks(savedBarcode.getRemarks());
        responseDTO.setCreatedAt(savedBarcode.getCreatedAt());

        return responseDTO;
    }

    @Override
    public BarcodeQRCodeResponseDTO getBarcodeQRCodeById(Long id) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Barcode/QR Code not found"));

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(barcodeQRCode.getId());
        responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
        responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
        responseDTO.setBarcode(barcodeQRCode.getBarcode());
        responseDTO.setQrCode(barcodeQRCode.getQrCode());
        responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
        responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
        responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
        responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
        responseDTO.setRemarks(barcodeQRCode.getRemarks());
        responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());

        return responseDTO;
    }

    @Override
    public BarcodeQRCodeResponseDTO updateBarcodeQRCode(Long id, BarcodeQRCodeRequestDTO requestDTO) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Barcode/QR Code not found"));

        Medicine medicine = medicineRepository.findById(requestDTO.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        barcodeQRCode.setMedicine(medicine);
        barcodeQRCode.setBarcode(requestDTO.getBarcode());
        barcodeQRCode.setQrCode(requestDTO.getQrCode());
        barcodeQRCode.setBarcodeType(requestDTO.getBarcodeType());
        barcodeQRCode.setVerificationStatus(requestDTO.getVerificationStatus());
        barcodeQRCode.setTotalScans(requestDTO.getTotalScans());
        barcodeQRCode.setRemarks(requestDTO.getRemarks());

        BarcodeQRCode updatedBarcode = barcodeQRCodeRepository.save(barcodeQRCode);

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(updatedBarcode.getId());
        responseDTO.setMedicineId(updatedBarcode.getMedicine().getId());
        responseDTO.setMedicineName(updatedBarcode.getMedicine().getMedicineName());
        responseDTO.setBarcode(updatedBarcode.getBarcode());
        responseDTO.setQrCode(updatedBarcode.getQrCode());
        responseDTO.setBarcodeType(updatedBarcode.getBarcodeType());
        responseDTO.setVerificationStatus(updatedBarcode.getVerificationStatus());
        responseDTO.setTotalScans(updatedBarcode.getTotalScans());
        responseDTO.setLastScannedAt(updatedBarcode.getLastScannedAt());
        responseDTO.setRemarks(updatedBarcode.getRemarks());
        responseDTO.setCreatedAt(updatedBarcode.getCreatedAt());

        return responseDTO;
    }

    @Override
    public void deleteBarcodeQRCode(Long id) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Barcode/QR Code not found"));

        barcodeQRCodeRepository.delete(barcodeQRCode);
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> getAllBarcodeQRCodes() {

        List<BarcodeQRCode> barcodeList = barcodeQRCodeRepository.findAll();

        List<BarcodeQRCodeResponseDTO> responseList = new ArrayList<>();

        for (BarcodeQRCode barcodeQRCode : barcodeList) {

            BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

            responseDTO.setId(barcodeQRCode.getId());
            responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
            responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
            responseDTO.setBarcode(barcodeQRCode.getBarcode());
            responseDTO.setQrCode(barcodeQRCode.getQrCode());
            responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
            responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
            responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
            responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
            responseDTO.setRemarks(barcodeQRCode.getRemarks());
            responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());

            responseList.add(responseDTO);
        }

        return responseList;
    }

    @Override
    public BarcodeQRCodeResponseDTO getByBarcode(String barcode) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByBarcode(barcode).orElseThrow(() ->
                        new RuntimeException("Barcode not found"));

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(barcodeQRCode.getId());
        responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
        responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
        responseDTO.setBarcode(barcodeQRCode.getBarcode());
        responseDTO.setQrCode(barcodeQRCode.getQrCode());
        responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
        responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
        responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
        responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
        responseDTO.setRemarks(barcodeQRCode.getRemarks());
        responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());

        return responseDTO;
    }

    @Override
    public BarcodeQRCodeResponseDTO getByQrCode(String qrCode) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByQrCode(qrCode).orElseThrow(() ->
                        new RuntimeException("QR Code not found"));

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(barcodeQRCode.getId());
        responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
        responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
        responseDTO.setBarcode(barcodeQRCode.getBarcode());
        responseDTO.setQrCode(barcodeQRCode.getQrCode());
        responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
        responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
        responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
        responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
        responseDTO.setRemarks(barcodeQRCode.getRemarks());
        responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());

        return responseDTO;
    }

    @Override
    public BarcodeQRCodeResponseDTO getByMedicineId(Long medicineId) {

        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByMedicine(medicine).orElseThrow(() ->
                        new RuntimeException("Barcode/QR Code not found"));

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(barcodeQRCode.getId());
        responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
        responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
        responseDTO.setBarcode(barcodeQRCode.getBarcode());
        responseDTO.setQrCode(barcodeQRCode.getQrCode());
        responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
        responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
        responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
        responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
        responseDTO.setRemarks(barcodeQRCode.getRemarks());
        responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());

        return responseDTO;
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> getByBarcodeType(BarcodeType barcodeType) {

        List<BarcodeQRCode> barcodeList = barcodeQRCodeRepository.findByBarcodeType(barcodeType);

        List<BarcodeQRCodeResponseDTO> responseList = new ArrayList<>();

        for (BarcodeQRCode barcodeQRCode : barcodeList) {

            BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

            responseDTO.setId(barcodeQRCode.getId());
            responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
            responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
            responseDTO.setBarcode(barcodeQRCode.getBarcode());
            responseDTO.setQrCode(barcodeQRCode.getQrCode());
            responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
            responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
            responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
            responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
            responseDTO.setRemarks(barcodeQRCode.getRemarks());
            responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());

            responseList.add(responseDTO);
        }

        return responseList;
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> getByVerificationStatus(VerificationStatus verificationStatus) {

        List<BarcodeQRCode> barcodeList = barcodeQRCodeRepository.findByVerificationStatus(verificationStatus);

        List<BarcodeQRCodeResponseDTO> responseList = new ArrayList<>();

        for (BarcodeQRCode barcodeQRCode : barcodeList) {

            BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

            responseDTO.setId(barcodeQRCode.getId());
            responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
            responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
            responseDTO.setBarcode(barcodeQRCode.getBarcode());
            responseDTO.setQrCode(barcodeQRCode.getQrCode());
            responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
            responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
            responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
            responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
            responseDTO.setRemarks(barcodeQRCode.getRemarks());
            responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());

            responseList.add(responseDTO);
        }

        return responseList;
    }

    @Override
    public BarcodeQRCodeResponseDTO verifyBarcode(String barcode) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByBarcode(barcode).orElseThrow(() ->
                        new RuntimeException("Barcode not found"));

        barcodeQRCode.setVerificationStatus(VerificationStatus.VERIFIED);
        barcodeQRCode.setTotalScans(barcodeQRCode.getTotalScans() + 1);
        barcodeQRCode.setLastScannedAt(LocalDateTime.now());

        BarcodeQRCode updatedBarcode = barcodeQRCodeRepository.save(barcodeQRCode);

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(updatedBarcode.getId());
        responseDTO.setMedicineId(updatedBarcode.getMedicine().getId());
        responseDTO.setMedicineName(updatedBarcode.getMedicine().getMedicineName());
        responseDTO.setBarcode(updatedBarcode.getBarcode());
        responseDTO.setQrCode(updatedBarcode.getQrCode());
        responseDTO.setBarcodeType(updatedBarcode.getBarcodeType());
        responseDTO.setVerificationStatus(updatedBarcode.getVerificationStatus());
        responseDTO.setTotalScans(updatedBarcode.getTotalScans());
        responseDTO.setLastScannedAt(updatedBarcode.getLastScannedAt());
        responseDTO.setRemarks(updatedBarcode.getRemarks());
        responseDTO.setCreatedAt(updatedBarcode.getCreatedAt());

        return responseDTO;
    }

    @Override
    public BarcodeDashboardDTO getBarcodeDashboard() {

        BarcodeDashboardDTO dto = new BarcodeDashboardDTO();

        dto.setTotalBarcodes(barcodeQRCodeRepository.count());

        dto.setVerifiedBarcodes(barcodeQRCodeRepository.countByVerificationStatus(VerificationStatus.VERIFIED));

        dto.setPendingVerification(barcodeQRCodeRepository.countByVerificationStatus(VerificationStatus.PENDING));

        dto.setFailedVerification(barcodeQRCodeRepository.countByVerificationStatus(VerificationStatus.FAILED));

        dto.setBarcodeCount(barcodeQRCodeRepository.countByBarcodeType(BarcodeType.BARCODE));

        dto.setQrCodeCount(barcodeQRCodeRepository.countByBarcodeType(BarcodeType.QR_CODE));

        return dto;
    }

}

