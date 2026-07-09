package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.PaymentMethod;
import com.arogyamed.healthcare.model.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

    private Long orderId;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

}
