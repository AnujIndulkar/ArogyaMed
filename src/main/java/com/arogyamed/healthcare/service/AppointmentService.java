package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AppointmentRequestDTO;
import com.arogyamed.healthcare.dto.AppointmentResponseDTO;
import com.arogyamed.healthcare.model.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentResponseDTO createAppointment(AppointmentRequestDTO request);

    AppointmentResponseDTO getAppointmentById(Long id);

    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO request);

    List<AppointmentResponseDTO> getAllAppointments();

    // ================= Search =================

    List<AppointmentResponseDTO> searchByPatientName(String fullName);

    List<AppointmentResponseDTO> searchByDoctorName(String fullName);

    List<AppointmentResponseDTO> searchByStatus(AppointmentStatus status);

    List<AppointmentResponseDTO> searchByAppointmentDate(LocalDate appointmentDate);

    List<AppointmentResponseDTO> searchByAppointmentDateRange(LocalDate startDate, LocalDate endDate);

    List<AppointmentResponseDTO> searchByReason(String reason);
}
