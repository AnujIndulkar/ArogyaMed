package com.arogyamed.healthcare.dto;

import lombok.*;
import com.arogyamed.healthcare.model.SOSStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SOSRequestDTO {

    private Long patientId;

    private String emergencyType;

    private String location;

    private Double latitude;

    private Double longitude;

    private SOSStatus status;
}
