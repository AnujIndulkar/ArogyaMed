package com.arogyamed.healthcare.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDTO {

    @NotNull(message = "Medicine ID is required")
    private Long medicineId;

    @NotNull(message = "Quantity available is required")
    @PositiveOrZero(message = "Quantity available cannot be negative")
    private Integer quantityAvailable;

    @NotNull(message = "Minimum stock level is required")
    @PositiveOrZero(message = "Minimum stock level cannot be negative")
    private Integer minimumStockLevel;
}