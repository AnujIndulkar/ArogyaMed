package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long pharmacistId;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be greater than zero")
    private Double totalAmount;

    private OrderStatus status;
}
