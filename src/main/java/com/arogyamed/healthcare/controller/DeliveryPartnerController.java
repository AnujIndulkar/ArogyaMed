package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.DeliveryPartnerRequestDTO;
import com.arogyamed.healthcare.dto.DeliveryPartnerResponseDTO;
import com.arogyamed.healthcare.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @PostMapping
    public DeliveryPartnerResponseDTO createDeliveryPartner(@RequestBody DeliveryPartnerRequestDTO request) {
        return deliveryPartnerService.createDeliveryPartner(request);
    }

    @GetMapping("/{userId}")
    public DeliveryPartnerResponseDTO getDeliveryPartnerByUserId(@PathVariable Long userId) {
        return deliveryPartnerService.getDeliveryPartnerByUserId(userId);
    }

    @PutMapping("/{userId}")
    public DeliveryPartnerResponseDTO updateDeliveryPartner(@PathVariable Long userId, @RequestBody DeliveryPartnerRequestDTO request) {
        return deliveryPartnerService.updateDeliveryPartner(userId, request);
    }
}
