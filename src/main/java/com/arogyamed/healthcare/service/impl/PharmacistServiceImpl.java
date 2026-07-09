package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.PharmacistRequestDTO;
import com.arogyamed.healthcare.dto.PharmacistResponseDTO;
import com.arogyamed.healthcare.model.Pharmacist;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.PharmacistRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacistServiceImpl implements PharmacistService {

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PharmacistResponseDTO createPharmacist(PharmacistRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                        new RuntimeException("User not found"));

        Pharmacist pharmacist = new Pharmacist();

        pharmacist.setUser(user);
        pharmacist.setPharmacyName(request.getPharmacyName());
        pharmacist.setLicenseNumber(request.getLicenseNumber());
        pharmacist.setExperienceYears(request.getExperienceYears());
        pharmacist.setPharmacyAddress(request.getPharmacyAddress());

        return mapToDTO(pharmacistRepository.save(pharmacist));
    }

    @Override
    public PharmacistResponseDTO getPharmacistByUserId(Long userId) {

        Pharmacist pharmacist = pharmacistRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Pharmacist not found"));

        return mapToDTO(pharmacist);
    }

    @Override
    public PharmacistResponseDTO updatePharmacist(Long userId, PharmacistRequestDTO request) {

        Pharmacist pharmacist = pharmacistRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Pharmacist not found"));

        pharmacist.setPharmacyName(request.getPharmacyName());
        pharmacist.setLicenseNumber(request.getLicenseNumber());
        pharmacist.setExperienceYears(request.getExperienceYears());
        pharmacist.setPharmacyAddress(request.getPharmacyAddress());

        return mapToDTO(pharmacistRepository.save(pharmacist));
    }

    private PharmacistResponseDTO mapToDTO(Pharmacist pharmacist) {

        PharmacistResponseDTO dto = new PharmacistResponseDTO();

        dto.setId(pharmacist.getId());

        dto.setUserId(pharmacist.getUser().getId());
        dto.setFullName(pharmacist.getUser().getFullName());
        dto.setEmail(pharmacist.getUser().getEmail());

        dto.setPharmacyName(pharmacist.getPharmacyName());
        dto.setLicenseNumber(pharmacist.getLicenseNumber());
        dto.setExperienceYears(pharmacist.getExperienceYears());
        dto.setPharmacyAddress(pharmacist.getPharmacyAddress());

        return dto;
    }
}
