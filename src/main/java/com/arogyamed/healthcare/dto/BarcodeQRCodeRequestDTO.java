package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.BarcodeType;
import com.arogyamed.healthcare.model.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeQRCodeRequestDTO {

    private Long medicineId;

    private String barcode;

    private String qrCode;

    private BarcodeType barcodeType;

    private VerificationStatus verificationStatus;

    private Integer totalScans;

    private String remarks;

    private Boolean active;

}