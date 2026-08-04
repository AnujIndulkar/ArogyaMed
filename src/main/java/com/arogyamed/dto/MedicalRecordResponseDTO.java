package com.arogyamed.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordResponseDTO {

    private Long id;

    private Long patientId;

    private String patientName;

    private String diagnosis;

    private String treatment;

    private String doctorNotes;

    private LocalDate visitDate;
}
