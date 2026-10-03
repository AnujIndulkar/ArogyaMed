package com.arogyamed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QualityInspectorResponseDTO {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String qualification;

    private String licenseNumber;

    private Integer yearsOfExperience;

    private String availabilityStatus;
}
