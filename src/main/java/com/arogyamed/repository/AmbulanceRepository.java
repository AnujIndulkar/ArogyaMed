package com.arogyamed.repository;

import com.arogyamed.model.Ambulance;
import com.arogyamed.model.AmbulanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {

    Optional<Ambulance> findByAmbulanceNumber(String ambulanceNumber);

    Optional<Ambulance> findByUserId(Long userId);

    // ================= Search =================

    List<Ambulance> findByAmbulanceNumberContainingIgnoreCase(String ambulanceNumber);

    List<Ambulance> findByDriverNameContainingIgnoreCase(String driverName);

    List<Ambulance> findByDriverPhoneContaining(String driverPhone);

    List<Ambulance> findByCurrentLocationContainingIgnoreCase(String currentLocation);

    List<Ambulance> findByStatus(AmbulanceStatus status);

    List<Ambulance> findByAvailable(Boolean available);

    List<Ambulance> findByRegistrationNumberContainingIgnoreCase(String registrationNumber);

    List<Ambulance> findByVerified(Boolean verified);

    List<Ambulance> findByInsuranceExpiryDateBefore(LocalDate date);

    List<Ambulance> findByFitnessCertificateExpiryDateBefore(LocalDate date);

    List<Ambulance> findByPollutionExpiryDateBefore(LocalDate date);
}