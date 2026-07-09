package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.DeliveryStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingRequestDTO {

    private Long orderId;

    private Long deliveryPartnerId;

    private DeliveryStatus status;

    private String remarks;

}
