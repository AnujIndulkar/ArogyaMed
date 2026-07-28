package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.DeliveryStatus;
import com.arogyamed.healthcare.model.DeliveryTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, Long> {

    long countByStatus(DeliveryStatus status);

    // ================= Search =================

    // Search by Order ID
    List<DeliveryTracking> findByOrderId(Long orderId);

    // Search by Patient Name
    List<DeliveryTracking> findByOrder_Patient_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Patient Email
    List<DeliveryTracking> findByOrder_Patient_User_EmailContainingIgnoreCase(String email);

    // Search by Delivery Partner Name
    List<DeliveryTracking> findByDeliveryPartner_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Delivery Partner Email
    List<DeliveryTracking> findByDeliveryPartner_User_EmailContainingIgnoreCase(String email);

    // Search by Vehicle Number
    List<DeliveryTracking> findByDeliveryPartner_VehicleNumberContainingIgnoreCase(String vehicleNumber);

    // Search by Availability Status
    List<DeliveryTracking> findByDeliveryPartner_AvailabilityStatusContainingIgnoreCase(String availabilityStatus);

    // Search by Delivery Status
    List<DeliveryTracking> findByStatus(DeliveryStatus status);

    // Search by Assigned Date
    List<DeliveryTracking> findByAssignedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Search by Dispatched Date
    List<DeliveryTracking> findByDispatchedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Search by Delivered Date
    List<DeliveryTracking> findByDeliveredAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Search by Remarks
    List<DeliveryTracking> findByRemarksContainingIgnoreCase(String remarks);

}
