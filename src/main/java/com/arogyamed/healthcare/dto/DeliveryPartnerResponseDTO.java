package com.arogyamed.healthcare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerResponseDTO {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String vehicleNumber;

    private String vehicleType;

    private String drivingLicenseNumber;

    private String availabilityStatus;
}
