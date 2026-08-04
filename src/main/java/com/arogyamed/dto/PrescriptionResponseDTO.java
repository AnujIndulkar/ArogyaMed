package com.arogyamed.dto;

import com.arogyamed.model.PrescriptionStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String prescriptionImageUrl;

    private String clinicName;

    private PrescriptionStatus status;

    private String rejectionReason;

    private LocalDateTime uploadedAt;
}