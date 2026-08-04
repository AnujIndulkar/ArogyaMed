package com.arogyamed.service;

import com.arogyamed.dto.DeliveryTrackingRequestDTO;
import com.arogyamed.dto.DeliveryTrackingResponseDTO;
import com.arogyamed.model.DeliveryStatus;

import java.time.LocalDateTime;

import java.util.List;

public interface DeliveryTrackingService {

    DeliveryTrackingResponseDTO createDelivery(DeliveryTrackingRequestDTO request);

    DeliveryTrackingResponseDTO getDeliveryById(Long id);

    DeliveryTrackingResponseDTO updateDelivery(Long id, DeliveryTrackingRequestDTO request);

    List<DeliveryTrackingResponseDTO> getAllDeliveries();

    // ================= Search =================

    List<DeliveryTrackingResponseDTO> searchByOrderId(Long orderId);

    List<DeliveryTrackingResponseDTO> searchByPatientName(String fullName);

    List<DeliveryTrackingResponseDTO> searchByPatientEmail(String email);

    List<DeliveryTrackingResponseDTO> searchByDeliveryPartnerName(String fullName);

    List<DeliveryTrackingResponseDTO> searchByDeliveryPartnerEmail(String email);

    List<DeliveryTrackingResponseDTO> searchByVehicleNumber(String vehicleNumber);

    List<DeliveryTrackingResponseDTO> searchByAvailabilityStatus(String availabilityStatus);

    List<DeliveryTrackingResponseDTO> searchByStatus(DeliveryStatus status);

    List<DeliveryTrackingResponseDTO> searchByAssignedDate(LocalDateTime startDate, LocalDateTime endDate);

    List<DeliveryTrackingResponseDTO> searchByDispatchedDate(LocalDateTime startDate, LocalDateTime endDate);

    List<DeliveryTrackingResponseDTO> searchByDeliveredDate(LocalDateTime startDate, LocalDateTime endDate);

    List<DeliveryTrackingResponseDTO> searchByRemarks(String remarks);

}
