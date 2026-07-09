package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AppointmentRequestDTO;
import com.arogyamed.healthcare.dto.AppointmentResponseDTO;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDTO createAppointment(AppointmentRequestDTO request);

    AppointmentResponseDTO getAppointmentById(Long id);

    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO request);

    List<AppointmentResponseDTO> getAllAppointments();
}
