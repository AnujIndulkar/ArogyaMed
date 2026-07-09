package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.DeliveryPartnerRequestDTO;
import com.arogyamed.healthcare.dto.DeliveryPartnerResponseDTO;

public interface DeliveryPartnerService {

    DeliveryPartnerResponseDTO createDeliveryPartner(DeliveryPartnerRequestDTO request);

    DeliveryPartnerResponseDTO getDeliveryPartnerByUserId(Long userId);

    DeliveryPartnerResponseDTO updateDeliveryPartner(Long userId, DeliveryPartnerRequestDTO request);
}
