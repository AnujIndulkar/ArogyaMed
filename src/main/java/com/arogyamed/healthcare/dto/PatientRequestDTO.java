package com.arogyamed.healthcare.dto;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 130, message = "Age must be realistic")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String bloodGroup;

    @Positive(message = "Height must be a positive number")
    private Double height;

    @Positive(message = "Weight must be a positive number")
    private Double weight;

    private String allergies;

    private String medicalHistory;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private String emergencyContactName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Emergency contact number must be exactly 10 digits")
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

    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be exactly 6 digits")
    private String pincode;
}