package com.arogyamed.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@Table(name = "sos_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SOS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String emergencyType;

    private String location;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private SOSStatus status;

    private LocalDateTime createdAt;

}
