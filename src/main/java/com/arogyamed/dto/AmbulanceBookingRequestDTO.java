package com.arogyamed.dto;

import com.arogyamed.model.BookingStatus;
import com.arogyamed.model.BookingType;
import com.arogyamed.model.EmergencyLevel;
import com.arogyamed.model.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceBookingRequestDTO {

    private Long patientId;

    private Long ambulanceId;

    private Long sosId;

    private BookingType bookingType;

    private String pickupLocation;

    private String destination;

    private BookingStatus status;

    private String hospitalName;

    private EmergencyLevel emergencyLevel;

    private PaymentStatus paymentStatus;

    private Integer etaMinutes;
}