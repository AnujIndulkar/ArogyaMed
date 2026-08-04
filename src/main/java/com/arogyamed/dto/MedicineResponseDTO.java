package com.arogyamed.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponseDTO {

    private Long id;

    private Long companyId;

    private String companyName;

    private String medicineName;

    private String category;

    private String description;

    private Double price;

    private String batchNumber;

    private LocalDate manufacturingDate;

    private LocalDate expiryDate;

    private Integer stockQuantity;

    private String imageUrl;
}