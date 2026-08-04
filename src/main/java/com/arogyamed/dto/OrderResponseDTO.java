package com.arogyamed.dto;

import com.arogyamed.model.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;

    private Long patientId;

    private String patientName;

    private Long pharmacistId;

    private String pharmacistName;

    private Double totalAmount;

    private OrderStatus status;

    private LocalDateTime orderDate;

}
