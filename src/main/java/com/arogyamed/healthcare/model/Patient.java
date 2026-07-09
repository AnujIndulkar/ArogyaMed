package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link with User (NO inheritance rule followed)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private Integer age;

    private String gender;

    private String bloodGroup;

    private Double height;

    private Double weight;

    private String allergies;

    private String medicalHistory;

    private LocalDate dateOfBirth;
}
