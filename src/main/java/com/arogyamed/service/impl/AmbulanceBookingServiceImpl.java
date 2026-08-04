package com.arogyamed.service.impl;

import com.arogyamed.dto.AmbulanceBookingRequestDTO;
import com.arogyamed.dto.AmbulanceBookingResponseDTO;
import com.arogyamed.model.*;
import com.arogyamed.model.*;
import com.arogyamed.repository.AmbulanceBookingRepository;
import com.arogyamed.repository.AmbulanceRepository;
import com.arogyamed.repository.PatientRepository;
import com.arogyamed.repository.SOSRepository;
import com.arogyamed.service.AmbulanceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbulanceBookingServiceImpl
        implements AmbulanceBookingService {

    @Autowired
    private AmbulanceBookingRepository bookingRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private SOSRepository sosRepository;

    @Override
    public AmbulanceBookingResponseDTO createBooking(AmbulanceBookingRequestDTO request) {

        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
                new RuntimeException("Patient not found"));

        Ambulance ambulance = ambulanceRepository.findById(request.getAmbulanceId()).orElseThrow(() ->
                new RuntimeException("Ambulance not found"));

        // Check ambulance availability
        if (!ambulance.isAvailable()) {
            throw new RuntimeException("Ambulance is already booked");
        }

        AmbulanceBooking booking = new AmbulanceBooking();

        booking.setPatient(patient);
        booking.setAmbulance(ambulance);

        // SOS Booking
        if (request.getBookingType() == BookingType.SOS) {

            SOS sos = sosRepository.findById(request.getSosId()).orElseThrow(() ->
                    new RuntimeException("SOS Request not found"));

            booking.setSos(sos);

            // Update SOS Status
            sos.setStatus(SOSStatus.AMBULANCE_ASSIGNED);

            sosRepository.save(sos);
        }

        booking.setBookingType(request.getBookingType());

        booking.setPickupLocation(request.getPickupLocation());

        booking.setDestination(request.getDestination());

        booking.setStatus(request.getStatus());

        booking.setBookedAt(LocalDateTime.now());

        booking.setHospitalName(request.getHospitalName());

        booking.setEmergencyLevel(request.getEmergencyLevel());

        booking.setPaymentStatus(request.getPaymentStatus());

        booking.setEtaMinutes(request.getEtaMinutes());

        // Update Ambulance
        ambulance.setAvailable(false);
        ambulance.setStatus(AmbulanceStatus.ON_DUTY);

        ambulanceRepository.save(ambulance);

        AmbulanceBooking savedBooking = bookingRepository.save(booking);

        return mapToDTO(savedBooking);
    }

    @Override
    public AmbulanceBookingResponseDTO getBookingById(Long id) {

        AmbulanceBooking booking = bookingRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Booking not found"));

        return mapToDTO(booking);
    }
    @Override
    public AmbulanceBookingResponseDTO updateBooking(Long id, AmbulanceBookingRequestDTO request) {

        AmbulanceBooking booking = bookingRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Booking not found"));

        booking.setPickupLocation(request.getPickupLocation());

        booking.setDestination(request.getDestination());

        booking.setStatus(request.getStatus());

        booking.setHospitalName(request.getHospitalName());

        booking.setEmergencyLevel(request.getEmergencyLevel());

        booking.setPaymentStatus(request.getPaymentStatus());

        booking.setEtaMinutes(request.getEtaMinutes());

        // If booking is completed
        if (request.getStatus() == BookingStatus.COMPLETED) {

            booking.setCompletedAt(LocalDateTime.now());

            // Make ambulance available again
            Ambulance ambulance = booking.getAmbulance();

            ambulance.setAvailable(true);
            ambulance.setStatus(AmbulanceStatus.AVAILABLE);

            ambulanceRepository.save(ambulance);

            // If this booking was created from SOS,
            // update SOS status also
            if (booking.getSos() != null) {

                SOS sos = booking.getSos();

                sos.setStatus(SOSStatus.COMPLETED);

                sosRepository.save(sos);
            }
        }

        AmbulanceBooking updatedBooking = bookingRepository.save(booking);

        return mapToDTO(updatedBooking);
    }

    @Override
    public List<AmbulanceBookingResponseDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= Enterprise Search & Filtering =================

    @Override
    public List<AmbulanceBookingResponseDTO> searchByPatientId(Long patientId) {
        return bookingRepository.findByPatient_Id(patientId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByPatientName(String patientName) {
        return bookingRepository.findByPatient_User_FullNameContainingIgnoreCase(patientName)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByAmbulanceId(Long ambulanceId) {
        return bookingRepository.findByAmbulance_Id(ambulanceId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByVehicleNumber(String vehicleNumber) {
        return bookingRepository.findByAmbulance_AmbulanceNumberContainingIgnoreCase(vehicleNumber)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByDriverName(String driverName) {
        return bookingRepository.findByAmbulance_DriverNameContainingIgnoreCase(driverName)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByDriverPhone(String driverPhone) {
        return bookingRepository.findByAmbulance_DriverPhone(driverPhone)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByHospitalName(String hospitalName) {
        return bookingRepository.findByHospitalNameContainingIgnoreCase(hospitalName)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByPickupLocation(String pickupLocation) {
        return bookingRepository.findByPickupLocationContainingIgnoreCase(pickupLocation)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByDestination(String destination) {
        return bookingRepository.findByDestinationContainingIgnoreCase(destination)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByBookingType(BookingType bookingType) {
        return bookingRepository.findByBookingType(bookingType)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByEmergencyLevel(EmergencyLevel emergencyLevel) {
        return bookingRepository.findByEmergencyLevel(emergencyLevel)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByStatusAndEmergencyLevel(BookingStatus status, EmergencyLevel emergencyLevel) {
        return bookingRepository.findByStatusAndEmergencyLevel(status, emergencyLevel)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByPaymentStatus(PaymentStatus paymentStatus) {
        return bookingRepository.findByPaymentStatus(paymentStatus)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByEtaMax(Integer maxEta) {
        return bookingRepository.findByEtaMinutesLessThanEqual(maxEta)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByEtaRange(Integer minEta, Integer maxEta) {
        return bookingRepository.findByEtaMinutesBetween(minEta, maxEta)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByBookedAtBetween(startDate, endDate)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchByPatientAndStatus(Long patientId, BookingStatus status) {
        return bookingRepository.findByPatient_IdAndStatus(patientId, status)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AmbulanceBookingResponseDTO> searchBookings(
            BookingStatus status,
            EmergencyLevel emergencyLevel,
            PaymentStatus paymentStatus,
            BookingType bookingType,
            Long patientId,
            Long ambulanceId,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return bookingRepository.searchBookings(
                        status, emergencyLevel, paymentStatus, bookingType,
                        patientId, ambulanceId, startDate, endDate)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // ================= Helper =================

    private AmbulanceBookingResponseDTO mapToDTO(AmbulanceBooking booking) {

        AmbulanceBookingResponseDTO dto = new AmbulanceBookingResponseDTO();

        dto.setId(booking.getId());

        dto.setPatientId(booking.getPatient().getId());

        dto.setPatientName(booking.getPatient().getUser().getFullName());

        dto.setAmbulanceId(booking.getAmbulance().getId());

        dto.setAmbulanceNumber(booking.getAmbulance().getAmbulanceNumber());

        dto.setDriverName(booking.getAmbulance().getDriverName());

        dto.setDriverPhone(booking.getAmbulance().getDriverPhone());

        if (booking.getSos() != null) {

            dto.setSosId(booking.getSos().getId());
        }

        dto.setBookingType(booking.getBookingType());

        dto.setPickupLocation(booking.getPickupLocation());

        dto.setDestination(booking.getDestination());

        dto.setStatus(booking.getStatus());

        dto.setBookedAt(booking.getBookedAt());

        dto.setCompletedAt(booking.getCompletedAt());

        dto.setHospitalName(booking.getHospitalName());

        dto.setEmergencyLevel(booking.getEmergencyLevel());

        dto.setPaymentStatus(booking.getPaymentStatus());

        dto.setEtaMinutes(booking.getEtaMinutes());

        return dto;
    }
}