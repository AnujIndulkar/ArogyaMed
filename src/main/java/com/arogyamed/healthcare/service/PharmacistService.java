package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PharmacistRequestDTO;
import com.arogyamed.healthcare.dto.PharmacistResponseDTO;

public interface PharmacistService {

    PharmacistResponseDTO createPharmacist(PharmacistRequestDTO request);

    PharmacistResponseDTO getPharmacistByUserId(Long userId);

    PharmacistResponseDTO updatePharmacist(Long userId, PharmacistRequestDTO request);
}
