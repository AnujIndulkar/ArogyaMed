package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.KYCRequestDTO;
import com.arogyamed.healthcare.dto.KYCResponseDTO;

import java.util.List;

public interface KYCService {

    KYCResponseDTO submitKYC(KYCRequestDTO request);

    KYCResponseDTO getKYCById(Long id);

    KYCResponseDTO getKYCByUser(Long userId);

    List<KYCResponseDTO> getAllKYC();

    KYCResponseDTO approveKYC(Long id);

    KYCResponseDTO rejectKYC(Long id, String remarks);

}
