package com.arogyamed.service;

import com.arogyamed.dto.DeliveryPartnerRequestDTO;
import com.arogyamed.dto.DeliveryPartnerResponseDTO;

import java.util.List;

public interface DeliveryPartnerService {

    // ================= CRUD =================

    DeliveryPartnerResponseDTO createDeliveryPartner(DeliveryPartnerRequestDTO request);

    DeliveryPartnerResponseDTO getDeliveryPartnerByUserId(Long userId);

    DeliveryPartnerResponseDTO updateDeliveryPartner(Long userId, DeliveryPartnerRequestDTO request);

    // ================= Search =================

    // Search by Full Name
    List<DeliveryPartnerResponseDTO> searchByFullName(String fullName);

    // Search by Vehicle Number
    List<DeliveryPartnerResponseDTO> searchByVehicleNumber(String vehicleNumber);

    // Search by Vehicle Type
    List<DeliveryPartnerResponseDTO> searchByVehicleType(String vehicleType);

    // Search by Driving License Number
    List<DeliveryPartnerResponseDTO> searchByDrivingLicenseNumber(String drivingLicenseNumber);

    // Search by Availability Status
    List<DeliveryPartnerResponseDTO> searchByAvailabilityStatus(String availabilityStatus);

    // Search by Email
    List<DeliveryPartnerResponseDTO> searchByEmail(String email);

    // Search by Phone Number
    List<DeliveryPartnerResponseDTO> searchByPhoneNumber(String phoneNumber);
}
