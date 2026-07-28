package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.KYCRequestDTO;
import com.arogyamed.healthcare.dto.KYCResponseDTO;
import com.arogyamed.healthcare.model.KYCStatus;

import java.util.List;

public interface KYCService {

    KYCResponseDTO submitKYC(KYCRequestDTO request);

    KYCResponseDTO getKYCById(Long id);

    KYCResponseDTO getKYCByUser(Long userId);

    List<KYCResponseDTO> getAllKYC();

    KYCResponseDTO approveKYC(Long id);

    KYCResponseDTO rejectKYC(Long id, String remarks);

    // ================= Search =================

    List<KYCResponseDTO> searchByFullName(String fullName);

    List<KYCResponseDTO> searchByEmail(String email);

    List<KYCResponseDTO> searchByDocumentType(String documentType);

    List<KYCResponseDTO> searchByDocumentNumber(String documentNumber);

    List<KYCResponseDTO> searchByStatus(KYCStatus status);

    List<KYCResponseDTO> searchByRemarks(String remarks);
}