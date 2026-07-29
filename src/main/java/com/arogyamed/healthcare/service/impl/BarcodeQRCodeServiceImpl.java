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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        barcodeQRCode.setActive(requestDTO.getActive() != null ? requestDTO.getActive() : true);

        BarcodeQRCode savedBarcode = barcodeQRCodeRepository.save(barcodeQRCode);

        return mapToDTO(savedBarcode);
    }

    @Override
    public BarcodeQRCodeResponseDTO getBarcodeQRCodeById(Long id) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Barcode/QR Code not found"));

        return mapToDTO(barcodeQRCode);
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

        if (requestDTO.getActive() != null) {
            barcodeQRCode.setActive(requestDTO.getActive());
        }

        BarcodeQRCode updatedBarcode = barcodeQRCodeRepository.save(barcodeQRCode);

        return mapToDTO(updatedBarcode);
    }

    @Override
    public void deleteBarcodeQRCode(Long id) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Barcode/QR Code not found"));

        barcodeQRCodeRepository.delete(barcodeQRCode);
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> getAllBarcodeQRCodes() {

        return barcodeQRCodeRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public BarcodeQRCodeResponseDTO getByBarcode(String barcode) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByBarcode(barcode).orElseThrow(() ->
                new RuntimeException("Barcode not found"));

        return mapToDTO(barcodeQRCode);
    }

    @Override
    public BarcodeQRCodeResponseDTO getByQrCode(String qrCode) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByQrCode(qrCode).orElseThrow(() ->
                new RuntimeException("QR Code not found"));

        return mapToDTO(barcodeQRCode);
    }

    @Override
    public BarcodeQRCodeResponseDTO getByMedicineId(Long medicineId) {

        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() ->
                new RuntimeException("Medicine not found"));

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByMedicine(medicine).orElseThrow(() ->
                new RuntimeException("Barcode/QR Code not found"));

        return mapToDTO(barcodeQRCode);
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> getByBarcodeType(BarcodeType barcodeType) {

        return barcodeQRCodeRepository.findByBarcodeType(barcodeType)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> getByVerificationStatus(VerificationStatus verificationStatus) {

        return barcodeQRCodeRepository.findByVerificationStatus(verificationStatus)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public BarcodeQRCodeResponseDTO verifyBarcode(String barcode) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByBarcode(barcode).orElseThrow(() ->
                new RuntimeException("Barcode not found"));

        barcodeQRCode.setVerificationStatus(VerificationStatus.VERIFIED);
        barcodeQRCode.setTotalScans(barcodeQRCode.getTotalScans() + 1);
        barcodeQRCode.setLastScannedAt(LocalDateTime.now());

        BarcodeQRCode updatedBarcode = barcodeQRCodeRepository.save(barcodeQRCode);

        return mapToDTO(updatedBarcode);
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

    // ================= Verification / Status Actions =================

    @Override
    public BarcodeQRCodeResponseDTO flagAsCounterfeit(String barcode, String remarks) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findByBarcode(barcode).orElseThrow(() ->
                new RuntimeException("Barcode not found"));

        barcodeQRCode.setCounterfeitDetected(true);
        barcodeQRCode.setCounterfeitDetectedAt(LocalDateTime.now());
        barcodeQRCode.setVerificationStatus(VerificationStatus.FAILED);
        barcodeQRCode.setRemarks(remarks);

        return mapToDTO(barcodeQRCodeRepository.save(barcodeQRCode));
    }

    @Override
    public BarcodeQRCodeResponseDTO activate(Long id) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Barcode/QR Code not found"));

        barcodeQRCode.setActive(true);

        return mapToDTO(barcodeQRCodeRepository.save(barcodeQRCode));
    }

    @Override
    public BarcodeQRCodeResponseDTO deactivate(Long id) {

        BarcodeQRCode barcodeQRCode = barcodeQRCodeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Barcode/QR Code not found"));

        barcodeQRCode.setActive(false);

        return mapToDTO(barcodeQRCodeRepository.save(barcodeQRCode));
    }

    // ================= Enterprise Search & Filtering =================

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByMedicineName(String medicineName) {
        return barcodeQRCodeRepository.findByMedicine_MedicineNameContainingIgnoreCase(medicineName)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByGenericName(String genericName) {
        return barcodeQRCodeRepository.findByMedicine_GenericNameContainingIgnoreCase(genericName)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByCompanyName(String companyName) {
        return barcodeQRCodeRepository.findByMedicine_Company_CompanyNameContainingIgnoreCase(companyName)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByBatchNumber(String batchNumber) {
        return barcodeQRCodeRepository.findByMedicine_BatchNumberContainingIgnoreCase(batchNumber)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByBarcode(String barcode) {
        return barcodeQRCodeRepository.findByBarcodeContainingIgnoreCase(barcode)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByQrCode(String qrCode) {
        return barcodeQRCodeRepository.findByQrCodeContainingIgnoreCase(qrCode)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByManufacturingDateRange(LocalDate startDate, LocalDate endDate) {
        return barcodeQRCodeRepository.findByMedicine_ManufacturingDateBetween(startDate, endDate)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByExpiryDateRange(LocalDate startDate, LocalDate endDate) {
        return barcodeQRCodeRepository.findByMedicine_ExpiryDateBetween(startDate, endDate)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchExpired() {
        return barcodeQRCodeRepository.findByMedicine_ExpiryDateBefore(LocalDate.now())
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByScanCountMin(Integer minScans) {
        return barcodeQRCodeRepository.findByTotalScansGreaterThanEqual(minScans)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchByScanCountRange(Integer minScans, Integer maxScans) {
        return barcodeQRCodeRepository.findByTotalScansBetween(minScans, maxScans)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchCounterfeit() {
        return barcodeQRCodeRepository.findByCounterfeitDetectedTrue()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchActive() {
        return barcodeQRCodeRepository.findByActiveTrue()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchInactive() {
        return barcodeQRCodeRepository.findByActiveFalse()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BarcodeQRCodeResponseDTO> searchBarcodes(
            VerificationStatus verificationStatus,
            BarcodeType barcodeType,
            Boolean active,
            Boolean counterfeitDetected,
            String medicineName,
            String companyName) {

        return barcodeQRCodeRepository.searchBarcodes(
                        verificationStatus, barcodeType, active, counterfeitDetected,
                        medicineName, companyName)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // ================= Helper =================

    private BarcodeQRCodeResponseDTO mapToDTO(BarcodeQRCode barcodeQRCode) {

        BarcodeQRCodeResponseDTO responseDTO = new BarcodeQRCodeResponseDTO();

        responseDTO.setId(barcodeQRCode.getId());
        responseDTO.setMedicineId(barcodeQRCode.getMedicine().getId());
        responseDTO.setMedicineName(barcodeQRCode.getMedicine().getMedicineName());
        responseDTO.setGenericName(barcodeQRCode.getMedicine().getGenericName());
        responseDTO.setCompanyName(
                barcodeQRCode.getMedicine().getCompany() != null
                        ? barcodeQRCode.getMedicine().getCompany().getCompanyName()
                        : null
        );
        responseDTO.setBatchNumber(barcodeQRCode.getMedicine().getBatchNumber());
        responseDTO.setManufacturingDate(barcodeQRCode.getMedicine().getManufacturingDate());
        responseDTO.setExpiryDate(barcodeQRCode.getMedicine().getExpiryDate());
        responseDTO.setBarcode(barcodeQRCode.getBarcode());
        responseDTO.setQrCode(barcodeQRCode.getQrCode());
        responseDTO.setBarcodeType(barcodeQRCode.getBarcodeType());
        responseDTO.setVerificationStatus(barcodeQRCode.getVerificationStatus());
        responseDTO.setTotalScans(barcodeQRCode.getTotalScans());
        responseDTO.setLastScannedAt(barcodeQRCode.getLastScannedAt());
        responseDTO.setRemarks(barcodeQRCode.getRemarks());
        responseDTO.setCreatedAt(barcodeQRCode.getCreatedAt());
        responseDTO.setActive(barcodeQRCode.getActive());
        responseDTO.setCounterfeitDetected(barcodeQRCode.getCounterfeitDetected());
        responseDTO.setCounterfeitDetectedAt(barcodeQRCode.getCounterfeitDetectedAt());

        return responseDTO;
    }

}

