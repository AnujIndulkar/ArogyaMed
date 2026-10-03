package com.arogyamed.service;

import com.arogyamed.dto.QualityInspectorRequestDTO;
import com.arogyamed.dto.QualityInspectorResponseDTO;

import java.util.List;

public interface QualityInspectorService {

    QualityInspectorResponseDTO createQualityInspector(QualityInspectorRequestDTO request);

    QualityInspectorResponseDTO getQualityInspectorByUserId(Long userId);

    QualityInspectorResponseDTO updateQualityInspector(Long userId, QualityInspectorRequestDTO request);

    List<QualityInspectorResponseDTO> getAllQualityInspectors();

    // ================= Search =================

    List<QualityInspectorResponseDTO> searchByFullName(String fullName);

    List<QualityInspectorResponseDTO> searchByQualification(String qualification);

    List<QualityInspectorResponseDTO> searchByLicenseNumber(String licenseNumber);

    List<QualityInspectorResponseDTO> searchByAvailabilityStatus(String availabilityStatus);

    List<QualityInspectorResponseDTO> searchByEmail(String email);

    List<QualityInspectorResponseDTO> searchByPhoneNumber(String phoneNumber);
}
