package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AmbulanceBookingRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceBookingResponseDTO;

import java.util.List;

public interface AmbulanceBookingService {

    AmbulanceBookingResponseDTO createBooking(AmbulanceBookingRequestDTO request);

    AmbulanceBookingResponseDTO getBookingById(Long id);

    AmbulanceBookingResponseDTO updateBooking(Long id, AmbulanceBookingRequestDTO request);

    List<AmbulanceBookingResponseDTO> getAllBookings();
}
