package com.arogyamed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeDashboardDTO {

    private Long totalBarcodes;

    private Long verifiedBarcodes;

    private Long pendingVerification;

    private Long failedVerification;

    private Long barcodeCount;

    private Long qrCodeCount;

}
