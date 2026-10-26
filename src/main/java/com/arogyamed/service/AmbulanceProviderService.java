package com.arogyamed.service;

import com.arogyamed.dto.AmbulanceBookingResponseDTO;
import com.arogyamed.dto.AmbulanceProviderDTO;
import com.arogyamed.dto.AmbulanceResponseDTO;
import com.arogyamed.model.AmbulanceStatus;
import com.arogyamed.model.BookingStatus;

import java.util.List;

public interface AmbulanceProviderService {

    AmbulanceResponseDTO myAmbulance(String email);

    AmbulanceResponseDTO changeAmbulanceStatus(String email, AmbulanceStatus status);

    AmbulanceProviderDTO.Summary summary(String email);

    List<AmbulanceProviderDTO.SosRequest> openRequests(String email);

    AmbulanceBookingResponseDTO acceptRequest(String email, Long sosId, Integer etaMinutes);

    List<AmbulanceBookingResponseDTO> myBookings(String email);

    AmbulanceBookingResponseDTO changeBookingStatus(String email, Long bookingId, BookingStatus next);
}