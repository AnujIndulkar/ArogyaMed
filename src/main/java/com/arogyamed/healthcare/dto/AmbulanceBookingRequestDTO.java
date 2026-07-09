package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.BookingStatus;
import com.arogyamed.healthcare.model.BookingType;
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
}