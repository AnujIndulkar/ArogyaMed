package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.BookingStatus;
import com.arogyamed.healthcare.model.BookingType;
import com.arogyamed.healthcare.model.EmergencyLevel;
import com.arogyamed.healthcare.model.PaymentStatus;
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