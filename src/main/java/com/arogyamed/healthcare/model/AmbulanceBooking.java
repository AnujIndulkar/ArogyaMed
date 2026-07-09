package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ambulance_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "ambulance_id", nullable = false)
    private Ambulance ambulance;

    @OneToOne
    @JoinColumn(name = "sos_id", nullable = true)
    private SOS sos;

    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    private String pickupLocation;

    private String destination;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime bookedAt;

    private LocalDateTime completedAt;
}