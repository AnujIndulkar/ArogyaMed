package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.DoctorRequestDTO;
import com.arogyamed.healthcare.dto.DoctorResponseDTO;
import com.arogyamed.healthcare.model.Doctor;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.DoctorRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));

        Doctor doctor = new Doctor();

        doctor.setUser(user);
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setHospitalName(request.getHospitalName());
        doctor.setConsultationFee(request.getConsultationFee());

        return mapToDTO(doctorRepository.save(doctor));
    }

    @Override
    public DoctorResponseDTO getDoctorByUserId(Long userId) {

        Doctor doctor = doctorRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        return mapToDTO(doctor);
    }

    @Override
    public DoctorResponseDTO updateDoctor(Long userId, DoctorRequestDTO request) {

        Doctor doctor = doctorRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setHospitalName(request.getHospitalName());
        doctor.setConsultationFee(request.getConsultationFee());

        return mapToDTO(doctorRepository.save(doctor));
    }

    private DoctorResponseDTO mapToDTO(Doctor doctor) {

        DoctorResponseDTO dto = new DoctorResponseDTO();

        dto.setId(doctor.getId());
        dto.setUserId(doctor.getUser().getId());
        dto.setFullName(doctor.getUser().getFullName());
        dto.setEmail(doctor.getUser().getEmail());

        dto.setSpecialization(doctor.getSpecialization());
        dto.setQualification(doctor.getQualification());
        dto.setExperienceYears(doctor.getExperienceYears());
        dto.setLicenseNumber(doctor.getLicenseNumber());
        dto.setHospitalName(doctor.getHospitalName());
        dto.setConsultationFee(doctor.getConsultationFee());

        return dto;
    }
}
