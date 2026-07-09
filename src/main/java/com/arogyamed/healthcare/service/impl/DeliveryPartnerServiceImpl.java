package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.DeliveryPartnerRequestDTO;
import com.arogyamed.healthcare.dto.DeliveryPartnerResponseDTO;
import com.arogyamed.healthcare.model.DeliveryPartner;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.DeliveryPartnerRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DeliveryPartnerResponseDTO createDeliveryPartner(DeliveryPartnerRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));

        DeliveryPartner deliveryPartner = new DeliveryPartner();

        deliveryPartner.setUser(user);
        deliveryPartner.setVehicleNumber(request.getVehicleNumber());
        deliveryPartner.setVehicleType(request.getVehicleType());
        deliveryPartner.setDrivingLicenseNumber(request.getDrivingLicenseNumber());
        deliveryPartner.setAvailabilityStatus(request.getAvailabilityStatus());

        return mapToDTO(deliveryPartnerRepository.save(deliveryPartner));
    }

    @Override
    public DeliveryPartnerResponseDTO getDeliveryPartnerByUserId(Long userId) {

        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException("Delivery Partner not found"));

        return mapToDTO(deliveryPartner);
    }

    @Override
    public DeliveryPartnerResponseDTO updateDeliveryPartner(Long userId, DeliveryPartnerRequestDTO request) {

        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException("Delivery Partner not found"));

        deliveryPartner.setVehicleNumber(request.getVehicleNumber());
        deliveryPartner.setVehicleType(request.getVehicleType());
        deliveryPartner.setDrivingLicenseNumber(request.getDrivingLicenseNumber());
        deliveryPartner.setAvailabilityStatus(request.getAvailabilityStatus());

        return mapToDTO(deliveryPartnerRepository.save(deliveryPartner));
    }

    private DeliveryPartnerResponseDTO mapToDTO(DeliveryPartner deliveryPartner) {

        DeliveryPartnerResponseDTO dto = new DeliveryPartnerResponseDTO();

        dto.setId(deliveryPartner.getId());

        dto.setUserId(deliveryPartner.getUser().getId());

        dto.setFullName(deliveryPartner.getUser().getFullName());

        dto.setEmail(deliveryPartner.getUser().getEmail());

        dto.setVehicleNumber(deliveryPartner.getVehicleNumber());

        dto.setVehicleType(deliveryPartner.getVehicleType());

        dto.setDrivingLicenseNumber(deliveryPartner.getDrivingLicenseNumber());

        dto.setAvailabilityStatus(deliveryPartner.getAvailabilityStatus());

        return dto;
    }
}
