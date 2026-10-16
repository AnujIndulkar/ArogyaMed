package com.arogyamed.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StockItemDTO {
    private Long medicineId;
    private String medicineName;
    private String category;
    private String companyName;
    private String batchNumber;
    private Double price;
    private Integer stockQuantity;
    private LocalDate expiryDate;

    // OUT_OF_STOCK, LOW_STOCK or IN_STOCK
    private String stockStatus;

    // EXPIRED, EXPIRING_SOON (within 90 days) or OK
    private String expiryStatus;
}