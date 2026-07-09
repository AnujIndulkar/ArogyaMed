package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.DoctorRequestDTO;
import com.arogyamed.healthcare.dto.DoctorResponseDTO;

public interface DoctorService {

    DoctorResponseDTO createDoctor(DoctorRequestDTO request);

    DoctorResponseDTO getDoctorByUserId(Long userId);

    DoctorResponseDTO updateDoctor(Long userId, DoctorRequestDTO request);
}