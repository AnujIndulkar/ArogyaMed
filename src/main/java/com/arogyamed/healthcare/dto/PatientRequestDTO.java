package com.arogyamed.healthcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientRequestDTO {

    private Long userId;

    private Integer age;

    private String gender;

    private String bloodGroup;

    private Double height;

    private Double weight;

    private String allergies;

    private String medicalHistory;

    private LocalDate dateOfBirth;

    // getters & setters
}