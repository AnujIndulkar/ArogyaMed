package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.AppointmentRequestDTO;
import com.arogyamed.healthcare.dto.AppointmentResponseDTO;
import com.arogyamed.healthcare.model.Appointment;
import com.arogyamed.healthcare.model.Doctor;
import com.arogyamed.healthcare.model.Patient;
import com.arogyamed.healthcare.repository.AppointmentRepository;
import com.arogyamed.healthcare.repository.DoctorRepository;
import com.arogyamed.healthcare.repository.PatientRepository;
import com.arogyamed.healthcare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl
        implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO request) {

        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        return mapToDTO(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                                new RuntimeException("Appointment not found"));

        return mapToDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO request) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                                new RuntimeException("Appointment not found"));

        appointment.setAppointmentDate(request.getAppointmentDate());

        appointment.setAppointmentTime(request.getAppointmentTime());

        appointment.setReason(request.getReason());

        appointment.setStatus(request.getStatus());

        return mapToDTO(appointmentRepository.save(appointment));
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AppointmentResponseDTO mapToDTO(Appointment appointment) {

        AppointmentResponseDTO dto = new AppointmentResponseDTO();

        dto.setId(appointment.getId());

        dto.setPatientId(appointment.getPatient().getId());

        dto.setPatientName(appointment.getPatient().getUser().getFullName());

        dto.setDoctorId(appointment.getDoctor().getId());

        dto.setDoctorName(appointment.getDoctor().getUser().getFullName());

        dto.setAppointmentDate(appointment.getAppointmentDate());

        dto.setAppointmentTime(appointment.getAppointmentTime());

        dto.setReason(appointment.getReason());

        dto.setStatus(appointment.getStatus());

        return dto;
    }
}
