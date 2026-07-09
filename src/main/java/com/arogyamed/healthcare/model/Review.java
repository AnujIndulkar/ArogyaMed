package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient who gives review
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Review Type
    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    // Doctor Review
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // Pharmacist Review
    @ManyToOne
    @JoinColumn(name = "pharmacist_id")
    private Pharmacist pharmacist;

    // Ambulance Review
    @ManyToOne
    @JoinColumn(name = "ambulance_id")
    private Ambulance ambulance;

    // Delivery Partner Review
    @ManyToOne
    @JoinColumn(name = "delivery_partner_id")
    private DeliveryPartner deliveryPartner;

    // Medicine Review
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    // Rating (1-5)
    @Column(nullable = false)
    private Integer rating;

    // Review Comment
    @Column(length = 1000)
    private String comment;

    // Review Date
    private LocalDateTime reviewDate;

}
