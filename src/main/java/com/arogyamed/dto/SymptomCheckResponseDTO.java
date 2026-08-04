package com.arogyamed.dto;

import com.arogyamed.model.UrgencyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SymptomCheckResponseDTO {

    private String inputSymptoms;

    private List<String> possibleConditions;

    private String recommendedSpecialization;

    private UrgencyLevel urgencyLevel;

    private List<DoctorResponseDTO> recommendedDoctors;

    // true if the real AI model produced this, false if the rule-based fallback was used
    private Boolean aiGenerated;

    private String disclaimer;
}