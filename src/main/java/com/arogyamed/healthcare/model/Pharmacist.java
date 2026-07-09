package com.arogyamed.healthcare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pharmacists")
public class Pharmacist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String pharmacyName;

    private String licenseNumber;

    private Integer experienceYears;

    private String pharmacyAddress;

    public Pharmacist() {
    }

    public Pharmacist(Long id, User user, String pharmacyName,
                      String licenseNumber, Integer experienceYears,
                      String pharmacyAddress) {
        this.id = id;
        this.user = user;
        this.pharmacyName = pharmacyName;
        this.licenseNumber = licenseNumber;
        this.experienceYears = experienceYears;
        this.pharmacyAddress = pharmacyAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }
}