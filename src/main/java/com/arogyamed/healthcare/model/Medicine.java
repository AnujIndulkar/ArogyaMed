package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;

    private String category;

    private String description;

    private Double price;

    private String batchNumber;

    private LocalDate manufacturingDate;

    private LocalDate expiryDate;

    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
