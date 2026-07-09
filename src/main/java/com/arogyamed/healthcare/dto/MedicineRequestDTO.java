package com.arogyamed.healthcare.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequestDTO {

    private Long companyId;

    private String medicineName;

    private String category;

    private String description;

    private Double price;

    private String batchNumber;

    private LocalDate manufacturingDate;

    private LocalDate expiryDate;

    private Integer stockQuantity;
}
