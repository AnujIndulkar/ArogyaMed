package com.arogyamed.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "admins")
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminType adminType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminDepartment department;

    @Column(nullable = false)
    private String designation;

    private String officeLocation;

    @Column(nullable = false)
    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus status;
}
