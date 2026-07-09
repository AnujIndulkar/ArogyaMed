package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.DeliveryStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingResponseDTO {

    private Long id;

    private Long orderId;

    private Long deliveryPartnerId;

    private String deliveryPartnerName;

    private DeliveryStatus status;

    private LocalDateTime assignedAt;

    private LocalDateTime dispatchedAt;

    private LocalDateTime deliveredAt;

    private String remarks;

}
