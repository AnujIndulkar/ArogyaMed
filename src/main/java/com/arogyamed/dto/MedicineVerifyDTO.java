package com.arogyamed.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/** Public, safe-to-share view of a medicine. Used by the QR page and the barcode scanner. */
@Getter
@Builder
public class MedicineVerifyDTO {

    private Long id;
    private String medicineName;
    private String genericName;
    private String packSize;
    private String companyName;
    private String batchNumber;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private Double price;
    private String imageUrl;
    private String barcode;

    /** true when stock is above 0 */
    private boolean inStock;

    /** VALID, EXPIRING_SOON or EXPIRED */
    private String status;

    /** Short sentence the page can show under the status */
    private String statusMessage;
}
