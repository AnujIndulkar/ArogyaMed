package com.arogyamed.healthcare.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {

    private Long id;

    private Long orderId;

    private Long medicineId;

    private String medicineName;

    private Integer quantity;

    private Double price;

    private Double subtotal;

}
