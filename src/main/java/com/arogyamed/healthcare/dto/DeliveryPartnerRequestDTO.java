package com.arogyamed.healthcare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerRequestDTO {

    private Long userId;

    private String vehicleNumber;

    private String vehicleType;

    private String drivingLicenseNumber;

    private String availabilityStatus;
}
