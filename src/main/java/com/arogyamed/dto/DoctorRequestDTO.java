package com.arogyamed.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 60, message = "Experience must be realistic")
    private Integer experienceYears;

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @NotBlank(message = "Hospital name is required")
    private String hospitalName;

    @NotNull(message = "Consultation fee is required")
    @PositiveOrZero(message = "Consultation fee cannot be negative")
    private Double consultationFee;
}