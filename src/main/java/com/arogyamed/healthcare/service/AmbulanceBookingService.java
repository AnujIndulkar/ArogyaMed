package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AmbulanceBookingRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceBookingResponseDTO;
import com.arogyamed.healthcare.model.BookingStatus;
import com.arogyamed.healthcare.model.BookingType;
import com.arogyamed.healthcare.model.EmergencyLevel;
import com.arogyamed.healthcare.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AmbulanceBookingService {

    AmbulanceBookingResponseDTO createBooking(AmbulanceBookingRequestDTO request);

    AmbulanceBookingResponseDTO getBookingById(Long id);

    AmbulanceBookingResponseDTO updateBooking(Long id, AmbulanceBookingRequestDTO request);

    List<AmbulanceBookingResponseDTO> getAllBookings();

    // ================= Enterprise Search & Filtering =================

    List<AmbulanceBookingResponseDTO> searchByPatientId(Long patientId);

    List<AmbulanceBookingResponseDTO> searchByPatientName(String patientName);

    List<AmbulanceBookingResponseDTO> searchByAmbulanceId(Long ambulanceId);

    List<AmbulanceBookingResponseDTO> searchByVehicleNumber(String vehicleNumber);

    List<AmbulanceBookingResponseDTO> searchByDriverName(String driverName);

    List<AmbulanceBookingResponseDTO> searchByDriverPhone(String driverPhone);

    List<AmbulanceBookingResponseDTO> searchByHospitalName(String hospitalName);

    List<AmbulanceBookingResponseDTO> searchByPickupLocation(String pickupLocation);

    List<AmbulanceBookingResponseDTO> searchByDestination(String destination);

    List<AmbulanceBookingResponseDTO> searchByStatus(BookingStatus status);

    List<AmbulanceBookingResponseDTO> searchByBookingType(BookingType bookingType);

    List<AmbulanceBookingResponseDTO> searchByEmergencyLevel(EmergencyLevel emergencyLevel);

    List<AmbulanceBookingResponseDTO> searchByStatusAndEmergencyLevel(BookingStatus status, EmergencyLevel emergencyLevel);

    List<AmbulanceBookingResponseDTO> searchByPaymentStatus(PaymentStatus paymentStatus);

    List<AmbulanceBookingResponseDTO> searchByEtaMax(Integer maxEta);

    List<AmbulanceBookingResponseDTO> searchByEtaRange(Integer minEta, Integer maxEta);

    List<AmbulanceBookingResponseDTO> searchByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<AmbulanceBookingResponseDTO> searchByPatientAndStatus(Long patientId, BookingStatus status);

    List<AmbulanceBookingResponseDTO> searchBookings(
            BookingStatus status,
            EmergencyLevel emergencyLevel,
            PaymentStatus paymentStatus,
            BookingType bookingType,
            Long patientId,
            Long ambulanceId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
