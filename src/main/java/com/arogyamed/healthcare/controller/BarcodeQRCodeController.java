package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.BarcodeDashboardDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeRequestDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeResponseDTO;
import com.arogyamed.healthcare.model.BarcodeType;
import com.arogyamed.healthcare.model.VerificationStatus;
import com.arogyamed.healthcare.service.BarcodeQRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/barcodes")
public class BarcodeQRCodeController {

    @Autowired
    private BarcodeQRCodeService barcodeQRCodeService;

    // ==========================================================
    // CORE CRUD
    // ==========================================================

    @PostMapping
    public BarcodeQRCodeResponseDTO createBarcodeQRCode(@RequestBody BarcodeQRCodeRequestDTO requestDTO) {

        return barcodeQRCodeService.createBarcodeQRCode(requestDTO);
    }

    @GetMapping("/{id}")
    public BarcodeQRCodeResponseDTO getBarcodeQRCodeById(@PathVariable Long id) {

        return barcodeQRCodeService.getBarcodeQRCodeById(id);
    }

    @PutMapping("/{id}")
    public BarcodeQRCodeResponseDTO updateBarcodeQRCode(@PathVariable Long id,
                                                        @RequestBody BarcodeQRCodeRequestDTO requestDTO) {

        return barcodeQRCodeService.updateBarcodeQRCode(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBarcodeQRCode(@PathVariable Long id) {

        barcodeQRCodeService.deleteBarcodeQRCode(id);
    }

    @GetMapping
    public List<BarcodeQRCodeResponseDTO> getAllBarcodeQRCodes() {

        return barcodeQRCodeService.getAllBarcodeQRCodes();
    }

    @GetMapping("/barcode/{barcode}")
    public BarcodeQRCodeResponseDTO getByBarcode(@PathVariable String barcode) {

        return barcodeQRCodeService.getByBarcode(barcode);
    }

    @GetMapping("/qrcode/{qrCode}")
    public BarcodeQRCodeResponseDTO getByQrCode(@PathVariable String qrCode) {

        return barcodeQRCodeService.getByQrCode(qrCode);
    }

    @GetMapping("/medicine/{medicineId}")
    public BarcodeQRCodeResponseDTO getByMedicineId(@PathVariable Long medicineId) {

        return barcodeQRCodeService.getByMedicineId(medicineId);
    }

    @GetMapping("/type/{barcodeType}")
    public List<BarcodeQRCodeResponseDTO> getByBarcodeType(@PathVariable BarcodeType barcodeType) {

        return barcodeQRCodeService.getByBarcodeType(barcodeType);
    }

    @GetMapping("/status/{verificationStatus}")
    public List<BarcodeQRCodeResponseDTO> getByVerificationStatus(
            @PathVariable VerificationStatus verificationStatus) {

        return barcodeQRCodeService.getByVerificationStatus(verificationStatus);
    }

    @PutMapping("/verify/{barcode}")
    public BarcodeQRCodeResponseDTO verifyBarcode(@PathVariable String barcode) {

        return barcodeQRCodeService.verifyBarcode(barcode);
    }

    @GetMapping("/dashboard")
    public BarcodeDashboardDTO getBarcodeDashboard() {

        return barcodeQRCodeService.getBarcodeDashboard();
    }

    // ==========================================================
    // VERIFICATION / STATUS ACTIONS
    // ==========================================================

    @PutMapping("/flag-counterfeit/{barcode}")
    public BarcodeQRCodeResponseDTO flagAsCounterfeit(@PathVariable String barcode, @RequestParam String remarks) {

        return barcodeQRCodeService.flagAsCounterfeit(barcode, remarks);
    }

    @PutMapping("/{id}/activate")
    public BarcodeQRCodeResponseDTO activate(@PathVariable Long id) {

        return barcodeQRCodeService.activate(id);
    }

    @PutMapping("/{id}/deactivate")
    public BarcodeQRCodeResponseDTO deactivate(@PathVariable Long id) {

        return barcodeQRCodeService.deactivate(id);
    }

    // ==========================================================
    // SEARCH BY MEDICINE / COMPANY
    // ==========================================================

    @GetMapping("/search/medicine-name")
    public List<BarcodeQRCodeResponseDTO> searchByMedicineName(@RequestParam String medicineName) {
        return barcodeQRCodeService.searchByMedicineName(medicineName);
    }

    @GetMapping("/search/generic-name")
    public List<BarcodeQRCodeResponseDTO> searchByGenericName(@RequestParam String genericName) {
        return barcodeQRCodeService.searchByGenericName(genericName);
    }

    @GetMapping("/search/company")
    public List<BarcodeQRCodeResponseDTO> searchByCompanyName(@RequestParam String companyName) {
        return barcodeQRCodeService.searchByCompanyName(companyName);
    }

    @GetMapping("/search/batch-number")
    public List<BarcodeQRCodeResponseDTO> searchByBatchNumber(@RequestParam String batchNumber) {
        return barcodeQRCodeService.searchByBatchNumber(batchNumber);
    }

    // ==========================================================
    // SEARCH BY CODE
    // ==========================================================

    @GetMapping("/search/barcode")
    public List<BarcodeQRCodeResponseDTO> searchByBarcode(@RequestParam String barcode) {
        return barcodeQRCodeService.searchByBarcode(barcode);
    }

    @GetMapping("/search/qr-code")
    public List<BarcodeQRCodeResponseDTO> searchByQrCode(@RequestParam String qrCode) {
        return barcodeQRCodeService.searchByQrCode(qrCode);
    }

    // ==========================================================
    // SEARCH BY DATE
    // ==========================================================

    @GetMapping("/search/manufacturing-date-range")
    public List<BarcodeQRCodeResponseDTO> searchByManufacturingDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return barcodeQRCodeService.searchByManufacturingDateRange(startDate, endDate);
    }

    @GetMapping("/search/expiry-date-range")
    public List<BarcodeQRCodeResponseDTO> searchByExpiryDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return barcodeQRCodeService.searchByExpiryDateRange(startDate, endDate);
    }

    @GetMapping("/search/expired")
    public List<BarcodeQRCodeResponseDTO> searchExpired() {
        return barcodeQRCodeService.searchExpired();
    }

    // ==========================================================
    // SEARCH BY SCAN COUNT
    // ==========================================================

    @GetMapping("/search/scan-count-min")
    public List<BarcodeQRCodeResponseDTO> searchByScanCountMin(@RequestParam Integer minScans) {
        return barcodeQRCodeService.searchByScanCountMin(minScans);
    }

    @GetMapping("/search/scan-count-range")
    public List<BarcodeQRCodeResponseDTO> searchByScanCountRange(
            @RequestParam Integer minScans,
            @RequestParam Integer maxScans) {
        return barcodeQRCodeService.searchByScanCountRange(minScans, maxScans);
    }

    // ==========================================================
    // SEARCH BY COUNTERFEIT / ACTIVE STATE
    // ==========================================================

    @GetMapping("/search/counterfeit")
    public List<BarcodeQRCodeResponseDTO> searchCounterfeit() {
        return barcodeQRCodeService.searchCounterfeit();
    }

    @GetMapping("/search/active")
    public List<BarcodeQRCodeResponseDTO> searchActive() {
        return barcodeQRCodeService.searchActive();
    }

    @GetMapping("/search/inactive")
    public List<BarcodeQRCodeResponseDTO> searchInactive() {
        return barcodeQRCodeService.searchInactive();
    }

    // ==========================================================
    // COMBINED FILTERS
    // ==========================================================

    @GetMapping("/search/combined")
    public List<BarcodeQRCodeResponseDTO> searchBarcodes(
            @RequestParam(required = false) VerificationStatus verificationStatus,
            @RequestParam(required = false) BarcodeType barcodeType,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean counterfeitDetected,
            @RequestParam(required = false) String medicineName,
            @RequestParam(required = false) String companyName) {

        return barcodeQRCodeService.searchBarcodes(verificationStatus, barcodeType, active, counterfeitDetected, medicineName, companyName);
    }
}