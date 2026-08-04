package com.arogyamed.dto;

import com.arogyamed.model.PaymentMethod;
import com.arogyamed.model.PaymentStatus;
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
