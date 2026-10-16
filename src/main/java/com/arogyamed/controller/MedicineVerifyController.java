package com.arogyamed.controller;

import com.arogyamed.dto.MedicineVerifyDTO;
import com.arogyamed.model.Medicine;
import com.arogyamed.repository.MedicineRepository;
import com.arogyamed.util.MedicineCodeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/medicines")
public class MedicineVerifyController {

    private final MedicineRepository medicineRepository;

    public MedicineVerifyController(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    // Opened when someone scans the QR code
    @GetMapping("/verify/{id}")
    public MedicineVerifyDTO verifyById(@PathVariable Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No medicine found with this code"));
        return toDto(medicine);
    }

    // Used by the camera barcode scanner
    @GetMapping("/barcode/{code}")
    public MedicineVerifyDTO verifyByBarcode(@PathVariable String code) {
        Long id = MedicineCodeUtil.idFromBarcode(code);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This barcode is not recognised");
        }
        return verifyById(id);
    }

    private MedicineVerifyDTO toDto(Medicine m) {
        LocalDate today = LocalDate.now();
        String status;
        String message;

        if (m.getExpiryDate() == null) {
            status = "VALID";
            message = "Registered on ArogyaMed. Expiry date not recorded.";
        } else if (m.getExpiryDate().isBefore(today)) {
            status = "EXPIRED";
            message = "This medicine is past its expiry date. Do not use it.";
        } else if (ChronoUnit.DAYS.between(today, m.getExpiryDate()) <= 90) {
            status = "EXPIRING_SOON";
            message = "Registered on ArogyaMed, but it expires within 90 days.";
        } else {
            status = "VALID";
            message = "Registered on ArogyaMed and within its expiry date.";
        }

        return MedicineVerifyDTO.builder()
                .id(m.getId())
                .medicineName(m.getMedicineName())
                .genericName(m.getGenericName())
                .packSize(m.getPackSize())
                .companyName(m.getCompany() != null ? m.getCompany().getCompanyName() : null)
                .batchNumber(m.getBatchNumber())
                .manufacturingDate(m.getManufacturingDate())
                .expiryDate(m.getExpiryDate())
                .price(m.getPrice())
                .imageUrl(m.getImageUrl())
                .barcode(MedicineCodeUtil.barcodeFor(m.getId()))
                .inStock(m.getStockQuantity() != null && m.getStockQuantity() > 0)
                .status(status)
                .statusMessage(message)
                .build();
    }
}