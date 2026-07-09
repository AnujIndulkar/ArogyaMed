package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.AmbulanceStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceResponseDTO {

    private Long id;

    private String ambulanceNumber;

    private String driverName;

    private String driverPhone;

    private String currentLocation;

    private AmbulanceStatus status;

    private boolean available;

}
