package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.AppointmentRequestDTO;
import com.arogyamed.healthcare.dto.AppointmentResponseDTO;
import com.arogyamed.healthcare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public AppointmentResponseDTO createAppointment(@RequestBody AppointmentRequestDTO request) {
        return appointmentService.createAppointment(request);
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PutMapping("/{id}")
    public AppointmentResponseDTO updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequestDTO request) {
        return appointmentService.updateAppointment(id, request);
    }

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}
