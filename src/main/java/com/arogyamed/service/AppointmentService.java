package com.arogyamed.service;

import com.arogyamed.dto.AppointmentRequestDTO;
import com.arogyamed.dto.AppointmentResponseDTO;
import com.arogyamed.model.AppointmentStatus;

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
