package com.arogyamed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SymptomCheckRequestDTO {

    // Optional - link the check to a patient record
    private Long patientId;

    // Free text symptom description from the user
    private String symptoms;

    // Optional - age helps the model reason better (not stored)
    private Integer age;

    // Optional - "MALE" / "FEMALE" / "OTHER"
    private String gender;
}
