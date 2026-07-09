package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.PaymentMethod;
import com.arogyamed.healthcare.model.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private Long id;

    private Long orderId;

    private Double amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime paymentDate;

}
