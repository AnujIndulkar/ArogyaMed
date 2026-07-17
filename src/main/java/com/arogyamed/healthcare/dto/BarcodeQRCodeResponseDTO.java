package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.BarcodeType;
import com.arogyamed.healthcare.model.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeQRCodeResponseDTO {

    private Long id;

    private Long medicineId;

    private String medicineName;

    private String barcode;

    private String qrCode;

    private BarcodeType barcodeType;

    private VerificationStatus verificationStatus;

    private Integer totalScans;

    private LocalDateTime lastScannedAt;

    private String remarks;

    private LocalDateTime createdAt;

}
