package com.arogyamed.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quality_inspectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QualityInspector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String qualification;

    private String licenseNumber;

    private Integer yearsOfExperience;

    private String availabilityStatus;
}
