package com.arogyamed.healthcare.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {

    private Long id;

    private Long patientId;

    private String patientName;

    private Long doctorId;

    private String doctorName;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String reason;

    private String status;
}
