package com.arogyamed.healthcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequestDTO {

    private Long userId;

    private String specialization;

    private String qualification;

    private Integer experienceYears;

    private String licenseNumber;

    private String hospitalName;

    private Double consultationFee;
}
