package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {

    Optional<DeliveryPartner> findByUserId(Long userId);

    // ================= Search Methods =================

    List<DeliveryPartner> findByUser_FullNameContainingIgnoreCase(String fullName);

    List<DeliveryPartner> findByVehicleNumberContainingIgnoreCase(String vehicleNumber);

    List<DeliveryPartner> findByVehicleTypeContainingIgnoreCase(String vehicleType);

    List<DeliveryPartner> findByDrivingLicenseNumberContainingIgnoreCase(String drivingLicenseNumber);

    List<DeliveryPartner> findByAvailabilityStatusContainingIgnoreCase(String availabilityStatus);

    List<DeliveryPartner> findByUser_EmailContainingIgnoreCase(String email);

    List<DeliveryPartner> findByUser_PhoneNumberContaining(String phoneNumber);
}
