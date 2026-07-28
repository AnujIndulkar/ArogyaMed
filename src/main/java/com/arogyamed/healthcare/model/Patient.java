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

    @Column(length = 1000)
    private String medicalHistory;

    @Column(length = 1000)
    private String allergies;

    private LocalDate dateOfBirth;

    private String emergencyContactName;

    @Column(length = 20)
    private String emergencyContactNumber;

    private String occupation;

    private String maritalStatus;

    private String profileImage;

    private String insuranceProvider;

    @Column(unique = true)
    private String insurancePolicyNumber;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pincode;
}
