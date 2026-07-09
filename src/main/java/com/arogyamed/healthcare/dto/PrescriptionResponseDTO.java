package com.arogyamed.healthcare.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponseDTO {

    private Long id;

    private Long doctorId;

    private String doctorName;

    private Long patientId;

    private String patientName;

    private String diagnosis;

    private String medicines;

    private String dosageInstructions;

    private LocalDate prescriptionDate;

    private String notes;
}
