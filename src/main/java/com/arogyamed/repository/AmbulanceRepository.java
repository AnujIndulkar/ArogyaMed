package com.arogyamed.repository;

import com.arogyamed.model.Ambulance;
import com.arogyamed.model.AmbulanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {

    Optional<Ambulance> findByAmbulanceNumber(String ambulanceNumber);

    // ================= Search =================

    // Ambulance Number
    List<Ambulance> findByAmbulanceNumberContainingIgnoreCase(String ambulanceNumber);

    // Driver Name
    List<Ambulance> findByDriverNameContainingIgnoreCase(String driverName);

    // Driver Phone
    List<Ambulance> findByDriverPhoneContaining(String driverPhone);

    // Current Location
    List<Ambulance> findByCurrentLocationContainingIgnoreCase(String currentLocation);

    // Status
    List<Ambulance> findByStatus(AmbulanceStatus status);

    // Availability
    List<Ambulance> findByAvailable(Boolean available);

    // Registration Number
    List<Ambulance> findByRegistrationNumberContainingIgnoreCase(String registrationNumber);

    // Verification
    List<Ambulance> findByVerified(Boolean verified);

    // Insurance Expiry
    List<Ambulance> findByInsuranceExpiryDateBefore(LocalDate date);

    // Fitness Certificate Expiry
    List<Ambulance> findByFitnessCertificateExpiryDateBefore(LocalDate date);

    // Pollution Certificate Expiry
    List<Ambulance> findByPollutionExpiryDateBefore(LocalDate date);
}
