package com.arogyamed.service.impl;

import com.arogyamed.dto.AmbulanceBookingResponseDTO;
import com.arogyamed.dto.AmbulanceProviderDTO;
import com.arogyamed.dto.AmbulanceResponseDTO;
import com.arogyamed.model.*;
import com.arogyamed.repository.AmbulanceBookingRepository;
import com.arogyamed.repository.AmbulanceRepository;
import com.arogyamed.repository.SOSRepository;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.service.AmbulanceProviderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AmbulanceProviderServiceImpl implements AmbulanceProviderService {

    // trips that are still going on
    private static final Set<BookingStatus> ACTIVE =
            EnumSet.of(BookingStatus.BOOKED, BookingStatus.ACCEPTED, BookingStatus.ON_THE_WAY);

    // which status can follow which: the only moves a provider may make
    private static final Map<BookingStatus, Set<BookingStatus>> NEXT = Map.of(
            BookingStatus.BOOKED, EnumSet.of(BookingStatus.ACCEPTED, BookingStatus.CANCELLED),
            BookingStatus.ACCEPTED, EnumSet.of(BookingStatus.ON_THE_WAY, BookingStatus.CANCELLED),
            BookingStatus.ON_THE_WAY, EnumSet.of(BookingStatus.COMPLETED, BookingStatus.CANCELLED));

    private final UserRepository userRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final AmbulanceBookingRepository bookingRepository;
    private final SOSRepository sosRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AmbulanceProviderServiceImpl(UserRepository userRepository,
                                        AmbulanceRepository ambulanceRepository,
                                        AmbulanceBookingRepository bookingRepository,
                                        SOSRepository sosRepository) {
        this.userRepository = userRepository;
        this.ambulanceRepository = ambulanceRepository;
        this.bookingRepository = bookingRepository;
        this.sosRepository = sosRepository;
    }

    // ---------------------------------------------------------------- ambulance

    @Override
    @Transactional(readOnly = true)
    public AmbulanceResponseDTO myAmbulance(String email) {
        return toAmbulance(findAmbulance(email));
    }

    @Override
    @Transactional
    public AmbulanceResponseDTO changeAmbulanceStatus(String email, AmbulanceStatus status) {
        if (status == AmbulanceStatus.ON_DUTY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "On duty is set automatically when you accept a request");
        }

        Ambulance ambulance = findAmbulance(email);

        boolean busy = bookingRepository.findByAmbulance_Id(ambulance.getId()).stream()
                .anyMatch(b -> ACTIVE.contains(b.getStatus()));
        if (busy) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Finish or cancel your current trip first");
        }

        ambulance.setStatus(status);
        ambulance.setAvailable(status == AmbulanceStatus.AVAILABLE);

        return toAmbulance(ambulanceRepository.save(ambulance));
    }

    @Override
    @Transactional(readOnly = true)
    public AmbulanceProviderDTO.Summary summary(String email) {
        Ambulance ambulance = findAmbulance(email);
        List<AmbulanceBooking> mine = bookingRepository.findByAmbulance_Id(ambulance.getId());

        return new AmbulanceProviderDTO.Summary(
                String.valueOf(ambulance.getStatus()),
                sosRepository.findByStatus(SOSStatus.PENDING).size(),
                mine.stream().filter(b -> ACTIVE.contains(b.getStatus())).count(),
                mine.stream().filter(b -> b.getStatus() == BookingStatus.COMPLETED).count(),
                mine.size());
    }

    // ----------------------------------------------------------------- requests

    @Override
    @Transactional(readOnly = true)
    public List<AmbulanceProviderDTO.SosRequest> openRequests(String email) {
        findAmbulance(email); // must be a provider with a profile

        return sosRepository.findByStatus(SOSStatus.PENDING).stream()
                .sorted(Comparator.comparing(SOS::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(s -> new AmbulanceProviderDTO.SosRequest(
                        s.getId(),
                        s.getPatient() != null ? s.getPatient().getUser().getFullName() : "Unknown",
                        s.getEmergencyType(),
                        s.getLocation(),
                        s.getLatitude(),
                        s.getLongitude(),
                        s.getCreatedAt()))
                .toList();
    }

    @Override
    @Transactional
    public AmbulanceBookingResponseDTO acceptRequest(String email, Long sosId, Integer etaMinutes) {
        if (etaMinutes != null && (etaMinutes < 1 || etaMinutes > 180)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ETA must be between 1 and 180 minutes");
        }

        // lock our own ambulance row, then the request row: two providers cannot take the same request,
        // and one provider cannot take two requests at once
        Ambulance ambulance = findAmbulance(email);
        entityManager.refresh(ambulance, LockModeType.PESSIMISTIC_WRITE);

        SOS sos = entityManager.find(SOS.class, sosId, LockModeType.PESSIMISTIC_WRITE);
        if (sos == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }
        if (sos.getStatus() != SOSStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Another ambulance has already taken this request");
        }
        if (sos.getPatient() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This request has no patient");
        }
        if (ambulance.getStatus() != AmbulanceStatus.AVAILABLE || !ambulance.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Set your ambulance to Available before accepting a request");
        }

        AmbulanceBooking booking = new AmbulanceBooking();
        booking.setPatient(sos.getPatient());
        booking.setAmbulance(ambulance);
        booking.setSos(sos);
        booking.setBookingType(BookingType.SOS);
        booking.setPickupLocation(sos.getLocation());
        booking.setStatus(BookingStatus.ACCEPTED);
        booking.setBookedAt(LocalDateTime.now());
        booking.setEmergencyLevel(EmergencyLevel.HIGH);
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setEtaMinutes(etaMinutes);

        sos.setStatus(SOSStatus.AMBULANCE_ASSIGNED);
        ambulance.setStatus(AmbulanceStatus.ON_DUTY);
        ambulance.setAvailable(false);

        return toBooking(bookingRepository.save(booking));
    }

    // ----------------------------------------------------------------- bookings

    @Override
    @Transactional(readOnly = true)
    public List<AmbulanceBookingResponseDTO> myBookings(String email) {
        Ambulance ambulance = findAmbulance(email);

        return bookingRepository.findByAmbulance_Id(ambulance.getId()).stream()
                .sorted(Comparator.comparing(AmbulanceBooking::getBookedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(this::toBooking)
                .toList();
    }

    @Override
    @Transactional
    public AmbulanceBookingResponseDTO changeBookingStatus(String email, Long bookingId, BookingStatus next) {
        Ambulance ambulance = findAmbulance(email);

        AmbulanceBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        // you can only change your own trips
        if (!booking.getAmbulance().getId().equals(ambulance.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is not your booking");
        }

        Set<BookingStatus> allowed = NEXT.get(booking.getStatus());
        if (allowed == null || !allowed.contains(next)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A trip that is " + booking.getStatus() + " cannot become " + next);
        }

        booking.setStatus(next);
        SOS sos = booking.getSos();

        switch (next) {
            case ON_THE_WAY -> {
                if (sos != null) sos.setStatus(SOSStatus.IN_PROGRESS);
            }
            case COMPLETED -> {
                booking.setCompletedAt(LocalDateTime.now());
                if (sos != null) sos.setStatus(SOSStatus.COMPLETED);
                release(ambulance);
            }
            case CANCELLED -> {
                release(ambulance);
                if (sos != null) {
                    // put the emergency back in the queue so another ambulance can take it
                    sos.setStatus(SOSStatus.PENDING);
                    booking.setSos(null);
                }
            }
            default -> {
            }
        }

        return toBooking(bookingRepository.save(booking));
    }

    // ------------------------------------------------------------------ helpers

    private void release(Ambulance ambulance) {
        ambulance.setStatus(AmbulanceStatus.AVAILABLE);
        ambulance.setAvailable(true);
        ambulanceRepository.save(ambulance);
    }

    private Ambulance findAmbulance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        return ambulanceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Your ambulance profile is not set up yet"));
    }

    private AmbulanceResponseDTO toAmbulance(Ambulance a) {
        return new AmbulanceResponseDTO(
                a.getId(),
                a.getUser() != null ? a.getUser().getId() : null,
                a.getAmbulanceNumber(),
                a.getDriverName(),
                a.getDriverPhone(),
                a.getCurrentLocation(),
                a.getStatus(),
                a.isAvailable(),
                a.getRegistrationNumber(),
                a.getVerified());
    }

    private AmbulanceBookingResponseDTO toBooking(AmbulanceBooking b) {
        AmbulanceBookingResponseDTO dto = new AmbulanceBookingResponseDTO();
        dto.setId(b.getId());
        dto.setPatientId(b.getPatient().getId());
        dto.setPatientName(b.getPatient().getUser().getFullName());
        dto.setAmbulanceId(b.getAmbulance().getId());
        dto.setAmbulanceNumber(b.getAmbulance().getAmbulanceNumber());
        dto.setDriverName(b.getAmbulance().getDriverName());
        dto.setDriverPhone(b.getAmbulance().getDriverPhone());
        if (b.getSos() != null) {
            dto.setSosId(b.getSos().getId());
        }
        dto.setBookingType(b.getBookingType());
        dto.setPickupLocation(b.getPickupLocation());
        dto.setDestination(b.getDestination());
        dto.setStatus(b.getStatus());
        dto.setBookedAt(b.getBookedAt());
        dto.setCompletedAt(b.getCompletedAt());
        dto.setHospitalName(b.getHospitalName());
        dto.setEmergencyLevel(b.getEmergencyLevel());
        dto.setPaymentStatus(b.getPaymentStatus());
        dto.setEtaMinutes(b.getEtaMinutes());
        return dto;
    }
}