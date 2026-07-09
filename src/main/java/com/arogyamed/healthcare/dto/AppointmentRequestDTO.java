package com.arogyamed.healthcare.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    private Long patientId;

    private Long doctorId;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String reason;

    private String status;
}
