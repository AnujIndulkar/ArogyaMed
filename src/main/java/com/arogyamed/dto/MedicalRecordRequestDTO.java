package com.arogyamed.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordRequestDTO {

    private Long patientId;

    private String diagnosis;

    private String treatment;

    private String doctorNotes;

    private LocalDate visitDate;
}
