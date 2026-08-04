package com.arogyamed.dto;

import com.arogyamed.model.AmbulanceStatus;
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
