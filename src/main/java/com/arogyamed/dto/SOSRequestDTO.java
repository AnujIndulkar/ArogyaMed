package com.arogyamed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.arogyamed.model.SOSStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SOSRequestDTO {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotBlank(message = "Emergency type is required")
    private String emergencyType;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private SOSStatus status;
}