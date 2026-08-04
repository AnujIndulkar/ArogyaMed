package com.arogyamed.service.impl;

import com.arogyamed.dto.DeliveryTrackingRequestDTO;
import com.arogyamed.dto.DeliveryTrackingResponseDTO;
import com.arogyamed.model.DeliveryPartner;
import com.arogyamed.model.DeliveryStatus;
import com.arogyamed.model.DeliveryTracking;
import com.arogyamed.model.Order;
import com.arogyamed.repository.DeliveryPartnerRepository;
import com.arogyamed.repository.DeliveryTrackingRepository;
import com.arogyamed.repository.OrderRepository;
import com.arogyamed.service.DeliveryTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryTrackingServiceImpl implements DeliveryTrackingService {

    @Autowired
    private DeliveryTrackingRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    @Override
    public DeliveryTrackingResponseDTO createDelivery(DeliveryTrackingRequestDTO request) {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                        new RuntimeException("Order not found"));

        DeliveryPartner partner = deliveryPartnerRepository.findById(request.getDeliveryPartnerId()).orElseThrow(() ->
                        new RuntimeException("Delivery Partner not found"));

        DeliveryTracking delivery = new DeliveryTracking();

        delivery.setOrder(order);

        delivery.setDeliveryPartner(partner);

        if (request.getStatus() == null) {

            delivery.setStatus(DeliveryStatus.ASSIGNED);

        } else {

            delivery.setStatus(request.getStatus());
        }

        delivery.setAssignedAt(LocalDateTime.now());

        if (delivery.getStatus() == DeliveryStatus.OUT_FOR_DELIVERY) {

            delivery.setDispatchedAt(LocalDateTime.now());
        }

        if (delivery.getStatus() == DeliveryStatus.DELIVERED) {

            delivery.setDeliveredAt(LocalDateTime.now());
        }

        delivery.setRemarks(request.getRemarks());

        DeliveryTracking savedDelivery = deliveryRepository.save(delivery);

        return mapToDTO(savedDelivery);
    }

    @Override
    public DeliveryTrackingResponseDTO getDeliveryById(Long id) {

        DeliveryTracking delivery = deliveryRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Delivery record not found"));

        return mapToDTO(delivery);
    }

    @Override
    public DeliveryTrackingResponseDTO updateDelivery(Long id, DeliveryTrackingRequestDTO request) {

        DeliveryTracking delivery = deliveryRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Delivery record not found"));

        delivery.setStatus(request.getStatus());

        if (request.getStatus() == DeliveryStatus.OUT_FOR_DELIVERY && delivery.getDispatchedAt() == null) {

            delivery.setDispatchedAt(LocalDateTime.now());
        }

        if (request.getStatus() == DeliveryStatus.DELIVERED && delivery.getDeliveredAt() == null) {

            delivery.setDeliveredAt(LocalDateTime.now());
        }

        delivery.setRemarks(request.getRemarks());

        DeliveryTracking updatedDelivery = deliveryRepository.save(delivery);

        return mapToDTO(updatedDelivery);
    }

    @Override
    public List<DeliveryTrackingResponseDTO> getAllDeliveries() {

        return deliveryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private DeliveryTrackingResponseDTO mapToDTO(DeliveryTracking delivery) {

        DeliveryTrackingResponseDTO dto = new DeliveryTrackingResponseDTO();

        dto.setId(delivery.getId());

        dto.setOrderId(delivery.getOrder().getId());

        dto.setDeliveryPartnerId(delivery.getDeliveryPartner().getId());

        dto.setDeliveryPartnerName(delivery.getDeliveryPartner().getUser().getFullName());

        dto.setStatus(delivery.getStatus());

        dto.setAssignedAt(delivery.getAssignedAt());

        dto.setDispatchedAt(delivery.getDispatchedAt());

        dto.setDeliveredAt(delivery.getDeliveredAt());

        dto.setRemarks(delivery.getRemarks());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<DeliveryTrackingResponseDTO> searchByOrderId(Long orderId) {

        return mapToDTOList(deliveryRepository.findByOrderId(orderId));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByPatientName(String fullName) {

        return mapToDTOList(deliveryRepository.findByOrder_Patient_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByPatientEmail(String email) {

        return mapToDTOList(deliveryRepository.findByOrder_Patient_User_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByDeliveryPartnerName(String fullName) {

        return mapToDTOList(deliveryRepository.findByDeliveryPartner_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByDeliveryPartnerEmail(String email) {

        return mapToDTOList(deliveryRepository.findByDeliveryPartner_User_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByVehicleNumber(String vehicleNumber) {

        return mapToDTOList(deliveryRepository.findByDeliveryPartner_VehicleNumberContainingIgnoreCase(vehicleNumber));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByAvailabilityStatus(String availabilityStatus) {

        return mapToDTOList(deliveryRepository.findByDeliveryPartner_AvailabilityStatusContainingIgnoreCase(availabilityStatus));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByStatus(DeliveryStatus status) {

        return mapToDTOList(deliveryRepository.findByStatus(status));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByAssignedDate(LocalDateTime startDate,
                                                                  LocalDateTime endDate) {

        return mapToDTOList(deliveryRepository.findByAssignedAtBetween(startDate, endDate));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByDispatchedDate(LocalDateTime startDate,
                                                                    LocalDateTime endDate) {

        return mapToDTOList(deliveryRepository.findByDispatchedAtBetween(startDate, endDate));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByDeliveredDate(LocalDateTime startDate,
                                                                   LocalDateTime endDate) {

        return mapToDTOList(deliveryRepository.findByDeliveredAtBetween(startDate, endDate));
    }

    @Override
    public List<DeliveryTrackingResponseDTO> searchByRemarks(String remarks) {

        return mapToDTOList(deliveryRepository.findByRemarksContainingIgnoreCase(remarks));
    }

    private List<DeliveryTrackingResponseDTO> mapToDTOList(List<DeliveryTracking> deliveries) {

        return deliveries.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
