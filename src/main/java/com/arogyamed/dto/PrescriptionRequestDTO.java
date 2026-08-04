package com.arogyamed.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequestDTO {

    private Long doctorId;

    private Long patientId;

    private String diagnosis;

    private String medicines;

    private String dosageInstructions;

    private LocalDate prescriptionDate;

    private String notes;
}
