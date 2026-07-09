package com.arogyamed.healthcare.dto;

import lombok.*;
import com.arogyamed.healthcare.model.SOSStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SOSResponseDTO {

    private Long id;

    private Long patientId;

    private String patientName;

    private String emergencyType;

    private String location;

    private Double latitude;

    private Double longitude;

    private SOSStatus status;

    private LocalDateTime createdAt;
}
