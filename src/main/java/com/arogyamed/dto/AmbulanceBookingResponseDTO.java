package com.arogyamed.dto;

import com.arogyamed.model.BookingStatus;
import com.arogyamed.model.BookingType;
import com.arogyamed.model.EmergencyLevel;
import com.arogyamed.model.PaymentStatus;
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

    private String driverName;

    private String driverPhone;

    private Long sosId;

    private BookingType bookingType;

    private String pickupLocation;

    private String destination;

    private BookingStatus status;

    private LocalDateTime bookedAt;

    private LocalDateTime completedAt;

    private String hospitalName;

    private EmergencyLevel emergencyLevel;

    private PaymentStatus paymentStatus;

    private Integer etaMinutes;
}