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

    // ================= Search =================

    // Search by Patient Name
    @GetMapping("/search/patient")
    public List<AppointmentResponseDTO> searchByPatientName(@RequestParam String fullName) {

        return appointmentService.searchByPatientName(fullName);
    }

    // Search by Doctor Name
    @GetMapping("/search/doctor")
    public List<AppointmentResponseDTO> searchByDoctorName(@RequestParam String fullName) {

        return appointmentService.searchByDoctorName(fullName);
    }

    // Search by Appointment Status
    @GetMapping("/search/status")
    public List<AppointmentResponseDTO> searchByStatus(@RequestParam com.arogyamed.healthcare.model.AppointmentStatus status) {

        return appointmentService.searchByStatus(status);
    }

    // Search by Appointment Date
    @GetMapping("/search/date")
    public List<AppointmentResponseDTO> searchByAppointmentDate(@RequestParam java.time.LocalDate appointmentDate) {

        return appointmentService.searchByAppointmentDate(appointmentDate);
    }

    // Search by Appointment Date Range
    @GetMapping("/search/date-range")
    public List<AppointmentResponseDTO> searchByAppointmentDateRange(@RequestParam java.time.LocalDate startDate, @RequestParam java.time.LocalDate endDate) {

        return appointmentService.searchByAppointmentDateRange(startDate, endDate);
    }

    // Search by Reason
    @GetMapping("/search/reason")
    public List<AppointmentResponseDTO> searchByReason(@RequestParam String reason) {

        return appointmentService.searchByReason(reason);
    }
}
