package com.arogyamed.healthcare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private String emergencyContactName;

    private String emergencyContactNumber;

    private String occupation;

    private String maritalStatus;

    private String profileImage;

    private String insuranceProvider;

    private String insurancePolicyNumber;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pincode;
}