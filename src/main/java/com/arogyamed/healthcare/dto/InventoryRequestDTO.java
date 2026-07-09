package com.arogyamed.healthcare.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDTO {

    private Long medicineId;

    private Integer quantityAvailable;

    private Integer minimumStockLevel;
}
