package com.arogyamed.healthcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorResponseDTO {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String specialization;

    private String qualification;

    private Integer experienceYears;

    private String licenseNumber;

    private String hospitalName;

    private Double consultationFee;
}
