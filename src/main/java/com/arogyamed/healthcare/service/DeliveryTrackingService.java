package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.DeliveryTrackingRequestDTO;
import com.arogyamed.healthcare.dto.DeliveryTrackingResponseDTO;

import java.util.List;

public interface DeliveryTrackingService {

    DeliveryTrackingResponseDTO createDelivery(DeliveryTrackingRequestDTO request);

    DeliveryTrackingResponseDTO getDeliveryById(Long id);

    DeliveryTrackingResponseDTO updateDelivery(Long id, DeliveryTrackingRequestDTO request);

    List<DeliveryTrackingResponseDTO> getAllDeliveries();

}
