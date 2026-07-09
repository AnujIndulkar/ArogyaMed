package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.DeliveryTrackingRequestDTO;
import com.arogyamed.healthcare.dto.DeliveryTrackingResponseDTO;
import com.arogyamed.healthcare.service.DeliveryTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-tracking")
public class DeliveryTrackingController {

    @Autowired
    private DeliveryTrackingService deliveryTrackingService;

    @PostMapping
    public DeliveryTrackingResponseDTO createDelivery(@RequestBody DeliveryTrackingRequestDTO request) {
        return deliveryTrackingService.createDelivery(request);
    }

    @GetMapping("/{id}")
    public DeliveryTrackingResponseDTO getDeliveryById(@PathVariable Long id) {
        return deliveryTrackingService.getDeliveryById(id);
    }

    @PutMapping("/{id}")
    public DeliveryTrackingResponseDTO updateDelivery(@PathVariable Long id, @RequestBody DeliveryTrackingRequestDTO request) {
        return deliveryTrackingService.updateDelivery(id, request);
    }

    @GetMapping
    public List<DeliveryTrackingResponseDTO> getAllDeliveries() {
        return deliveryTrackingService.getAllDeliveries();
    }
}
