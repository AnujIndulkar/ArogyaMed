package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Appointment;
import com.arogyamed.healthcare.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Dashboard
    long countByStatus(AppointmentStatus status);

    // ================= Search =================

    // Search by Patient Name
    List<Appointment> findByPatient_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Doctor Name
    List<Appointment> findByDoctor_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Appointment Status
    List<Appointment> findByStatus(AppointmentStatus status);

    // Search by Appointment Date
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    // Search by Appointment Date Range
    List<Appointment> findByAppointmentDateBetween(LocalDate startDate, LocalDate endDate);

    // Search by Reason
    List<Appointment> findByReasonContainingIgnoreCase(String reason);
}
