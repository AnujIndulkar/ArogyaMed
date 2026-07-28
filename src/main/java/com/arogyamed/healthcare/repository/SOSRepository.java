package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.SOS;
import com.arogyamed.healthcare.model.SOSStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SOSRepository extends JpaRepository<SOS, Long> {

    // ================= Search =================

    // Search by Patient Name
    List<SOS> findByPatient_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Emergency Type
    List<SOS> findByEmergencyTypeContainingIgnoreCase(String emergencyType);

    // Search by Location
    List<SOS> findByLocationContainingIgnoreCase(String location);

    // Search by Status
    List<SOS> findByStatus(SOSStatus status);

    // Search by Created Date
    List<SOS> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
