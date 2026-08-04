package com.arogyamed.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    private Long orderId;

    private Long medicineId;

    private Integer quantity;

}
