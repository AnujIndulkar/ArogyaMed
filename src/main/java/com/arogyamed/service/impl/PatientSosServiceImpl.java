package com.arogyamed.service.impl;

import com.arogyamed.dto.PatientSosDTO;
import com.arogyamed.model.*;
import com.arogyamed.repository.AmbulanceBookingRepository;
import com.arogyamed.repository.PatientRepository;
import com.arogyamed.repository.SOSRepository;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.service.PatientSosService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientSosServiceImpl implements PatientSosService {

    // an emergency that is still going on
    private static final Set<SOSStatus> ACTIVE =
            EnumSet.of(SOSStatus.PENDING, SOSStatus.AMBULANCE_ASSIGNED, SOSStatus.IN_PROGRESS);

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final SOSRepository sosRepository;
    private final AmbulanceBookingRepository bookingRepository;

    public PatientSosServiceImpl(UserRepository userRepository,
                                 PatientRepository patientRepository,
                                 SOSRepository sosRepository,
                                 AmbulanceBookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.sosRepository = sosRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public PatientSosDTO.Status create(String email, PatientSosDTO.CreateRequest request) {
        Patient patient = findPatient(email);

        // pressing the button twice must not create two emergencies
        Optional<SOS> existing = findActive(patient);
        if (existing.isPresent()) {
            return toStatus(existing.get(), patient);
        }

        SOS sos = new SOS();
        sos.setPatient(patient);
        sos.setEmergencyType(blank(request.emergencyType()) ? "Medical emergency" : request.emergencyType().trim());
        sos.setLocation(blank(request.location())
                ? String.format("Lat %.5f, Lng %.5f", request.latitude(), request.longitude())
                : request.location().trim());
        sos.setLatitude(request.latitude());
        sos.setLongitude(request.longitude());
        sos.setStatus(SOSStatus.PENDING);
        sos.setCreatedAt(LocalDateTime.now());

        return toStatus(sosRepository.save(sos), patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientSosDTO.Status> active(String email) {
        Patient patient = findPatient(email);
        return findActive(patient).map(s -> toStatus(s, patient));
    }

    @Override
    @Transactional
    public PatientSosDTO.Status cancel(String email, Long sosId) {
        Patient patient = findPatient(email);

        SOS sos = sosRepository.findById(sosId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        if (sos.getPatient() == null || !sos.getPatient().getId().equals(patient.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is not your request");
        }
        if (sos.getStatus() != SOSStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "An ambulance has already taken your request. Please call the driver if you no longer need help");
        }

        sos.setStatus(SOSStatus.CANCELLED);
        return toStatus(sosRepository.save(sos), patient);
    }

    // ------------------------------------------------------------------ helpers

    private Optional<SOS> findActive(Patient patient) {
        return sosRepository.findByPatient_IdOrderByCreatedAtDesc(patient.getId()).stream()
                .filter(s -> ACTIVE.contains(s.getStatus()))
                .findFirst();
    }

    private Patient findPatient(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        return patientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Your patient profile is not set up yet"));
    }

    private PatientSosDTO.Status toStatus(SOS sos, Patient patient) {
        // the trip that an ambulance created for this emergency (if any ambulance accepted it)
        Optional<AmbulanceBooking> booking = bookingRepository.findByPatient_Id(patient.getId()).stream()
                .filter(b -> b.getSos() != null && b.getSos().getId().equals(sos.getId()))
                .filter(b -> b.getStatus() != BookingStatus.CANCELLED)
                .findFirst();

        return new PatientSosDTO.Status(
                sos.getId(),
                sos.getStatus(),
                sos.getEmergencyType(),
                sos.getLocation(),
                sos.getCreatedAt(),
                booking.map(b -> b.getStatus().name()).orElse(null),
                booking.map(b -> b.getAmbulance().getAmbulanceNumber()).orElse(null),
                booking.map(b -> b.getAmbulance().getDriverName()).orElse(null),
                booking.map(b -> b.getAmbulance().getDriverPhone()).orElse(null),
                booking.map(AmbulanceBooking::getEtaMinutes).orElse(null));
    }

    private boolean blank(String s) {
        return s == null || s.isBlank();
    }
}