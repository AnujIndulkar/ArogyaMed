package com.arogyamed.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDTO {

    private Long id;

    private Long medicineId;

    private String medicineName;

    private Integer quantityAvailable;

    private Integer minimumStockLevel;

    private LocalDateTime lastUpdated;
}
