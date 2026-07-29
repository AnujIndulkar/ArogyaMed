package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.AmbulanceBookingRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceBookingResponseDTO;
import com.arogyamed.healthcare.model.BookingStatus;
import com.arogyamed.healthcare.model.BookingType;
import com.arogyamed.healthcare.model.EmergencyLevel;
import com.arogyamed.healthcare.model.PaymentStatus;
import com.arogyamed.healthcare.service.AmbulanceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ambulance-bookings")
public class AmbulanceBookingController {

    @Autowired
    private AmbulanceBookingService bookingService;

    // ==========================================================
    // CORE CRUD
    // ==========================================================

    @PostMapping
    public AmbulanceBookingResponseDTO createBooking(@RequestBody AmbulanceBookingRequestDTO request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/{id}")
    public AmbulanceBookingResponseDTO getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public AmbulanceBookingResponseDTO updateBooking(@PathVariable Long id, @RequestBody AmbulanceBookingRequestDTO request) {
        return bookingService.updateBooking(id, request);
    }

    @GetMapping
    public List<AmbulanceBookingResponseDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // ==========================================================
    // SEARCH BY PATIENT
    // ==========================================================

    @GetMapping("/search/patient-id")
    public List<AmbulanceBookingResponseDTO> searchByPatientId(@RequestParam Long patientId) {
        return bookingService.searchByPatientId(patientId);
    }

    @GetMapping("/search/patient-name")
    public List<AmbulanceBookingResponseDTO> searchByPatientName(@RequestParam String patientName) {
        return bookingService.searchByPatientName(patientName);
    }

    // ==========================================================
    // SEARCH BY AMBULANCE / DRIVER / VEHICLE
    // ==========================================================

    @GetMapping("/search/ambulance-id")
    public List<AmbulanceBookingResponseDTO> searchByAmbulanceId(@RequestParam Long ambulanceId) {
        return bookingService.searchByAmbulanceId(ambulanceId);
    }

    @GetMapping("/search/vehicle-number")
    public List<AmbulanceBookingResponseDTO> searchByVehicleNumber(@RequestParam String vehicleNumber) {
        return bookingService.searchByVehicleNumber(vehicleNumber);
    }

    @GetMapping("/search/driver-name")
    public List<AmbulanceBookingResponseDTO> searchByDriverName(@RequestParam String driverName) {
        return bookingService.searchByDriverName(driverName);
    }

    @GetMapping("/search/driver-phone")
    public List<AmbulanceBookingResponseDTO> searchByDriverPhone(@RequestParam String driverPhone) {
        return bookingService.searchByDriverPhone(driverPhone);
    }

    // ==========================================================
    // SEARCH BY LOCATION
    // ==========================================================

    @GetMapping("/search/hospital")
    public List<AmbulanceBookingResponseDTO> searchByHospitalName(@RequestParam String hospitalName) {
        return bookingService.searchByHospitalName(hospitalName);
    }

    @GetMapping("/search/pickup")
    public List<AmbulanceBookingResponseDTO> searchByPickupLocation(@RequestParam String pickupLocation) {
        return bookingService.searchByPickupLocation(pickupLocation);
    }

    @GetMapping("/search/destination")
    public List<AmbulanceBookingResponseDTO> searchByDestination(@RequestParam String destination) {
        return bookingService.searchByDestination(destination);
    }

    // ==========================================================
    // SEARCH BY STATUS / TYPE / EMERGENCY LEVEL
    // ==========================================================

    @GetMapping("/search/status")
    public List<AmbulanceBookingResponseDTO> searchByStatus(@RequestParam BookingStatus status) {
        return bookingService.searchByStatus(status);
    }

    @GetMapping("/search/booking-type")
    public List<AmbulanceBookingResponseDTO> searchByBookingType(@RequestParam BookingType bookingType) {
        return bookingService.searchByBookingType(bookingType);
    }

    @GetMapping("/search/emergency-level")
    public List<AmbulanceBookingResponseDTO> searchByEmergencyLevel(@RequestParam EmergencyLevel emergencyLevel) {
        return bookingService.searchByEmergencyLevel(emergencyLevel);
    }

    @GetMapping("/search/status-and-emergency-level")
    public List<AmbulanceBookingResponseDTO> searchByStatusAndEmergencyLevel(
            @RequestParam BookingStatus status,
            @RequestParam EmergencyLevel emergencyLevel) {
        return bookingService.searchByStatusAndEmergencyLevel(status, emergencyLevel);
    }

    // ==========================================================
    // SEARCH BY PAYMENT STATUS
    // ==========================================================

    @GetMapping("/search/payment-status")
    public List<AmbulanceBookingResponseDTO> searchByPaymentStatus(@RequestParam PaymentStatus paymentStatus) {
        return bookingService.searchByPaymentStatus(paymentStatus);
    }

    // ==========================================================
    // SEARCH BY ETA
    // ==========================================================

    @GetMapping("/search/eta-max")
    public List<AmbulanceBookingResponseDTO> searchByEtaMax(@RequestParam Integer maxEta) {
        return bookingService.searchByEtaMax(maxEta);
    }

    @GetMapping("/search/eta-range")
    public List<AmbulanceBookingResponseDTO> searchByEtaRange(
            @RequestParam Integer minEta,
            @RequestParam Integer maxEta) {
        return bookingService.searchByEtaRange(minEta, maxEta);
    }

    // ==========================================================
    // SEARCH BY DATE RANGE
    // ==========================================================

    @GetMapping("/search/date-range")
    public List<AmbulanceBookingResponseDTO> searchByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return bookingService.searchByDateRange(startDate, endDate);
    }

    // ==========================================================
    // COMBINED FILTERS
    // ==========================================================

    @GetMapping("/search/patient-and-status")
    public List<AmbulanceBookingResponseDTO> searchByPatientAndStatus(
            @RequestParam Long patientId,
            @RequestParam BookingStatus status) {
        return bookingService.searchByPatientAndStatus(patientId, status);
    }

    @GetMapping("/search/combined")
    public List<AmbulanceBookingResponseDTO> searchBookings(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) EmergencyLevel emergencyLevel,
            @RequestParam(required = false) PaymentStatus paymentStatus,
            @RequestParam(required = false) BookingType bookingType,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long ambulanceId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {

        return bookingService.searchBookings(
                status, emergencyLevel, paymentStatus, bookingType,
                patientId, ambulanceId, startDate, endDate);
    }
}
