package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.BookingStatus;
import com.arogyamed.healthcare.model.BookingType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceBookingResponseDTO {

    private Long id;

    private Long patientId;

    private String patientName;

    private Long ambulanceId;

    private String ambulanceNumber;

    private Long sosId;

    private BookingType bookingType;

    private String pickupLocation;

    private String destination;

    private BookingStatus status;

    private LocalDateTime bookedAt;

    private LocalDateTime completedAt;
}
