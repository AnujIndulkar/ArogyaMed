package com.arogyamed.dto;

import com.arogyamed.model.OrderStatus;
import com.arogyamed.model.PaymentMethod;
import com.arogyamed.model.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutResponseDTO {

    private String patientName;
    private Long orderId;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    private Long paymentId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;

    private List<Line> items;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Line {
        private Long medicineId;
        private String medicineName;
        private Integer quantity;
        private Double price;
        private Double subtotal;
    }
}