package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private Long patientId;

    private Long pharmacistId;

    private Double totalAmount;

    private OrderStatus status;

}
