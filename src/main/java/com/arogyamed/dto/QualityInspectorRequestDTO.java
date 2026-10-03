package com.arogyamed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QualityInspectorRequestDTO {

    private Long userId;

    private String qualification;

    private String licenseNumber;

    private Integer yearsOfExperience;

    private String availabilityStatus;
}
