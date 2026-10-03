package com.arogyamed.service.impl;

import com.arogyamed.dto.QualityInspectorRequestDTO;
import com.arogyamed.dto.QualityInspectorResponseDTO;
import com.arogyamed.model.QualityInspector;
import com.arogyamed.model.User;
import com.arogyamed.repository.QualityInspectorRepository;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.service.QualityInspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QualityInspectorServiceImpl implements QualityInspectorService {

    @Autowired
    private QualityInspectorRepository qualityInspectorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public QualityInspectorResponseDTO createQualityInspector(QualityInspectorRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));

        QualityInspector qualityInspector = new QualityInspector();

        qualityInspector.setUser(user);
        qualityInspector.setQualification(request.getQualification());
        qualityInspector.setLicenseNumber(request.getLicenseNumber());
        qualityInspector.setYearsOfExperience(request.getYearsOfExperience());
        qualityInspector.setAvailabilityStatus(request.getAvailabilityStatus());

        return mapToDTO(qualityInspectorRepository.save(qualityInspector));
    }

    @Override
    public QualityInspectorResponseDTO getQualityInspectorByUserId(Long userId) {

        QualityInspector qualityInspector = qualityInspectorRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException("Quality Inspector not found"));

        return mapToDTO(qualityInspector);
    }

    @Override
    public QualityInspectorResponseDTO updateQualityInspector(Long userId, QualityInspectorRequestDTO request) {

        QualityInspector qualityInspector = qualityInspectorRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException("Quality Inspector not found"));

        qualityInspector.setQualification(request.getQualification());
        qualityInspector.setLicenseNumber(request.getLicenseNumber());
        qualityInspector.setYearsOfExperience(request.getYearsOfExperience());
        qualityInspector.setAvailabilityStatus(request.getAvailabilityStatus());

        return mapToDTO(qualityInspectorRepository.save(qualityInspector));
    }

    @Override
    public List<QualityInspectorResponseDTO> getAllQualityInspectors() {

        return qualityInspectorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private QualityInspectorResponseDTO mapToDTO(QualityInspector qualityInspector) {

        QualityInspectorResponseDTO dto = new QualityInspectorResponseDTO();

        dto.setId(qualityInspector.getId());
        dto.setUserId(qualityInspector.getUser().getId());
        dto.setFullName(qualityInspector.getUser().getFullName());
        dto.setEmail(qualityInspector.getUser().getEmail());
        dto.setPhoneNumber(qualityInspector.getUser().getPhoneNumber());
        dto.setQualification(qualityInspector.getQualification());
        dto.setLicenseNumber(qualityInspector.getLicenseNumber());
        dto.setYearsOfExperience(qualityInspector.getYearsOfExperience());
        dto.setAvailabilityStatus(qualityInspector.getAvailabilityStatus());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<QualityInspectorResponseDTO> searchByFullName(String fullName) {
        return mapToDTOList(qualityInspectorRepository.findByUser_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<QualityInspectorResponseDTO> searchByQualification(String qualification) {
        return mapToDTOList(qualityInspectorRepository.findByQualificationContainingIgnoreCase(qualification));
    }

    @Override
    public List<QualityInspectorResponseDTO> searchByLicenseNumber(String licenseNumber) {
        return mapToDTOList(qualityInspectorRepository.findByLicenseNumberContainingIgnoreCase(licenseNumber));
    }

    @Override
    public List<QualityInspectorResponseDTO> searchByAvailabilityStatus(String availabilityStatus) {
        return mapToDTOList(qualityInspectorRepository.findByAvailabilityStatusContainingIgnoreCase(availabilityStatus));
    }

    @Override
    public List<QualityInspectorResponseDTO> searchByEmail(String email) {
        return mapToDTOList(qualityInspectorRepository.findByUser_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<QualityInspectorResponseDTO> searchByPhoneNumber(String phoneNumber) {
        return mapToDTOList(qualityInspectorRepository.findByUser_PhoneNumberContaining(phoneNumber));
    }

    private List<QualityInspectorResponseDTO> mapToDTOList(List<QualityInspector> inspectors) {
        return inspectors.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
