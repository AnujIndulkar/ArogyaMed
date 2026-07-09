package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.WholesalerRequestDTO;
import com.arogyamed.healthcare.dto.WholesalerResponseDTO;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.model.Wholesaler;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.repository.WholesalerRepository;
import com.arogyamed.healthcare.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WholesalerServiceImpl implements WholesalerService {

    @Autowired
    private WholesalerRepository wholesalerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public WholesalerResponseDTO createWholesaler(WholesalerRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                        new RuntimeException("User not found"));

        Wholesaler wholesaler = new Wholesaler();

        wholesaler.setUser(user);
        wholesaler.setCompanyName(request.getCompanyName());
        wholesaler.setLicenseNumber(request.getLicenseNumber());
        wholesaler.setGstNumber(request.getGstNumber());
        wholesaler.setWarehouseAddress(request.getWarehouseAddress());
        wholesaler.setContactPerson(request.getContactPerson());

        return mapToDTO(wholesalerRepository.save(wholesaler));
    }

    @Override
    public WholesalerResponseDTO getWholesalerByUserId(Long userId) {

        Wholesaler wholesaler = wholesalerRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Wholesaler not found"));

        return mapToDTO(wholesaler);
    }

    @Override
    public WholesalerResponseDTO updateWholesaler(Long userId, WholesalerRequestDTO request) {

        Wholesaler wholesaler = wholesalerRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Wholesaler not found"));

        wholesaler.setCompanyName(request.getCompanyName());
        wholesaler.setLicenseNumber(request.getLicenseNumber());
        wholesaler.setGstNumber(request.getGstNumber());
        wholesaler.setWarehouseAddress(request.getWarehouseAddress());
        wholesaler.setContactPerson(request.getContactPerson());

        return mapToDTO(wholesalerRepository.save(wholesaler));
    }

    private WholesalerResponseDTO mapToDTO(Wholesaler wholesaler) {

        WholesalerResponseDTO dto = new WholesalerResponseDTO();

        dto.setId(wholesaler.getId());
        dto.setUserId(wholesaler.getUser().getId());
        dto.setFullName(wholesaler.getUser().getFullName());
        dto.setEmail(wholesaler.getUser().getEmail());

        dto.setCompanyName(wholesaler.getCompanyName());
        dto.setLicenseNumber(wholesaler.getLicenseNumber());
        dto.setGstNumber(wholesaler.getGstNumber());
        dto.setWarehouseAddress(wholesaler.getWarehouseAddress());
        dto.setContactPerson(wholesaler.getContactPerson());

        return dto;
    }
}
