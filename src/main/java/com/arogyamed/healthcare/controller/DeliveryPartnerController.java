package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.DeliveryPartnerRequestDTO;
import com.arogyamed.healthcare.dto.DeliveryPartnerResponseDTO;
import com.arogyamed.healthcare.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @PostMapping
    public DeliveryPartnerResponseDTO createDeliveryPartner(@RequestBody DeliveryPartnerRequestDTO request) {
        return deliveryPartnerService.createDeliveryPartner(request);
    }

    // ================= Search APIs =================

    @GetMapping("/{userId}")
    public DeliveryPartnerResponseDTO getDeliveryPartnerByUserId(@PathVariable Long userId) {
        return deliveryPartnerService.getDeliveryPartnerByUserId(userId);
    }

    // Search by Full Name
    @GetMapping("/search/full-name")
    public List<DeliveryPartnerResponseDTO> searchByFullName(@RequestParam String fullName) {
        return deliveryPartnerService.searchByFullName(fullName);
    }

    // Search by Vehicle Number
    @GetMapping("/search/vehicle-number")
    public List<DeliveryPartnerResponseDTO> searchByVehicleNumber(@RequestParam String vehicleNumber) {
        return deliveryPartnerService.searchByVehicleNumber(vehicleNumber);
    }

    // Search by Vehicle Type
    @GetMapping("/search/vehicle-type")
    public List<DeliveryPartnerResponseDTO> searchByVehicleType(@RequestParam String vehicleType) {
        return deliveryPartnerService.searchByVehicleType(vehicleType);
    }

    // Search by Driving License Number
    @GetMapping("/search/driving-license")
    public List<DeliveryPartnerResponseDTO> searchByDrivingLicenseNumber(@RequestParam String drivingLicenseNumber) {
        return deliveryPartnerService.searchByDrivingLicenseNumber(drivingLicenseNumber);
    }

    // Search by Availability Status
    @GetMapping("/search/availability")
    public List<DeliveryPartnerResponseDTO> searchByAvailabilityStatus(@RequestParam String availabilityStatus) {
        return deliveryPartnerService.searchByAvailabilityStatus(availabilityStatus);
    }

    // Search by Email
    @GetMapping("/search/email")
    public List<DeliveryPartnerResponseDTO> searchByEmail(@RequestParam String email) {
        return deliveryPartnerService.searchByEmail(email);
    }

    // Search by Phone Number
    @GetMapping("/search/phone-number")
    public List<DeliveryPartnerResponseDTO> searchByPhoneNumber(@RequestParam String phoneNumber) {
        return deliveryPartnerService.searchByPhoneNumber(phoneNumber);
    }

    @PutMapping("/{userId}")
    public DeliveryPartnerResponseDTO updateDeliveryPartner(@PathVariable Long userId, @RequestBody DeliveryPartnerRequestDTO request) {
        return deliveryPartnerService.updateDeliveryPartner(userId, request);
    }
}
