package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.BarcodeDashboardDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeRequestDTO;
import com.arogyamed.healthcare.dto.BarcodeQRCodeResponseDTO;
import com.arogyamed.healthcare.model.BarcodeType;
import com.arogyamed.healthcare.model.VerificationStatus;
import com.arogyamed.healthcare.service.BarcodeQRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barcodes")
public class BarcodeQRCodeController {

    @Autowired
    private BarcodeQRCodeService barcodeQRCodeService;

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

}
