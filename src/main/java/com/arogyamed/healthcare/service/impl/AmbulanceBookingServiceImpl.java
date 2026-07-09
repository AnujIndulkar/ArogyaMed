package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.AmbulanceBookingRequestDTO;
import com.arogyamed.healthcare.dto.AmbulanceBookingResponseDTO;
import com.arogyamed.healthcare.model.*;
import com.arogyamed.healthcare.repository.AmbulanceBookingRepository;
import com.arogyamed.healthcare.repository.AmbulanceRepository;
import com.arogyamed.healthcare.repository.PatientRepository;
import com.arogyamed.healthcare.repository.SOSRepository;
import com.arogyamed.healthcare.service.AmbulanceBookingService;
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

    private AmbulanceBookingResponseDTO mapToDTO(AmbulanceBooking booking) {

        AmbulanceBookingResponseDTO dto = new AmbulanceBookingResponseDTO();

        dto.setId(booking.getId());

        dto.setPatientId(booking.getPatient().getId());

        dto.setPatientName(booking.getPatient().getUser().getFullName());

        dto.setAmbulanceId(booking.getAmbulance().getId());

        dto.setAmbulanceNumber(booking.getAmbulance().getAmbulanceNumber());

        if (booking.getSos() != null) {

            dto.setSosId(booking.getSos().getId());
        }

        dto.setBookingType(booking.getBookingType());

        dto.setPickupLocation(booking.getPickupLocation());

        dto.setDestination(booking.getDestination());

        dto.setStatus(booking.getStatus());

        dto.setBookedAt(booking.getBookedAt());

        dto.setCompletedAt(booking.getCompletedAt());

        return dto;
    }
}