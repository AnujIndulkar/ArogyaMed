package com.arogyamed.service.impl;

import com.arogyamed.dto.KYCRequestDTO;
import com.arogyamed.dto.KYCResponseDTO;
import com.arogyamed.model.KYC;
import com.arogyamed.model.KYCStatus;
import com.arogyamed.model.User;
import com.arogyamed.repository.KYCRepository;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.service.KYCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KYCServiceImpl implements KYCService {

    @Autowired
    private KYCRepository kycRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public KYCResponseDTO submitKYC(KYCRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                        new RuntimeException("User not found"));
        // Only selected roles require KYC
        switch (user.getRole()) {

            case DOCTOR:
            case PHARMACIST:
            case WHOLESALER:
            case COMPANY:
            case DELIVERY_PARTNER:
                break;

            case PATIENT:
            case ADMIN:
                throw new RuntimeException("KYC is not required for this role.");

            default:
                throw new RuntimeException("Invalid user role.");
        }

        if (kycRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("KYC already submitted for this user");
        }

        KYC kyc = new KYC();

        kyc.setUser(user);

        kyc.setDocumentType(request.getDocumentType());

        kyc.setDocumentNumber(request.getDocumentNumber());

        kyc.setDocumentUrl(request.getDocumentUrl());

        // Default status
        kyc.setStatus(KYCStatus.PENDING);

        // Submission time
        kyc.setSubmittedAt(LocalDateTime.now());

        KYC savedKYC = kycRepository.save(kyc);

        return mapToDTO(savedKYC);
    }

    @Override
    public KYCResponseDTO getKYCById(Long id) {

        KYC kyc = kycRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("KYC not found"));

        return mapToDTO(kyc);
    }

    @Override
    public KYCResponseDTO getKYCByUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new RuntimeException("User not found"));

        KYC kyc = kycRepository.findByUser(user).orElseThrow(() ->
                        new RuntimeException("KYC not found"));

        return mapToDTO(kyc);
    }

    @Override
    public List<KYCResponseDTO> getAllKYC() {

        return kycRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KYCResponseDTO approveKYC(Long id) {

        KYC kyc = kycRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("KYC not found"));

        kyc.setStatus(KYCStatus.APPROVED);

        kyc.setRemarks(null);

        kyc.setVerifiedAt(LocalDateTime.now());

        KYC updatedKYC = kycRepository.save(kyc);

        return mapToDTO(updatedKYC);
    }

    @Override
    public KYCResponseDTO rejectKYC(Long id, String remarks) {

        KYC kyc = kycRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("KYC not found"));

        kyc.setStatus(KYCStatus.REJECTED);

        kyc.setRemarks(remarks);

        kyc.setVerifiedAt(LocalDateTime.now());

        KYC updatedKYC = kycRepository.save(kyc);

        return mapToDTO(updatedKYC);
    }

    private KYCResponseDTO mapToDTO(KYC kyc) {

        KYCResponseDTO dto = new KYCResponseDTO();

        dto.setId(kyc.getId());

        dto.setUserId(kyc.getUser().getId());

        dto.setUserName(kyc.getUser().getFullName());

        dto.setRole(kyc.getUser().getRole().name());

        dto.setDocumentType(kyc.getDocumentType());

        dto.setDocumentNumber(kyc.getDocumentNumber());

        dto.setDocumentUrl(kyc.getDocumentUrl());

        dto.setStatus(kyc.getStatus());

        dto.setRemarks(kyc.getRemarks());

        dto.setSubmittedAt(kyc.getSubmittedAt());

        dto.setVerifiedAt(kyc.getVerifiedAt());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<KYCResponseDTO> searchByFullName(String fullName) {

        return mapToDTOList(kycRepository.findByUser_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<KYCResponseDTO> searchByEmail(String email) {

        return mapToDTOList(kycRepository.findByUser_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<KYCResponseDTO> searchByDocumentType(String documentType) {

        return mapToDTOList(kycRepository.findByDocumentTypeContainingIgnoreCase(documentType));
    }

    @Override
    public List<KYCResponseDTO> searchByDocumentNumber(String documentNumber) {

        return mapToDTOList(kycRepository.findByDocumentNumberContainingIgnoreCase(documentNumber));
    }

    @Override
    public List<KYCResponseDTO> searchByStatus(KYCStatus status) {

        return mapToDTOList(kycRepository.findByStatus(status));
    }

    @Override
    public List<KYCResponseDTO> searchByRemarks(String remarks) {

        return mapToDTOList(kycRepository.findByRemarksContainingIgnoreCase(remarks));
    }

    private List<KYCResponseDTO> mapToDTOList(List<KYC> kycList) {

        return kycList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
