package com.arogyamed.controller;

import com.arogyamed.dto.DeliveryTrackingRequestDTO;
import com.arogyamed.dto.DeliveryTrackingResponseDTO;
import com.arogyamed.service.DeliveryTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.model.DeliveryStatus;
import java.time.LocalDateTime;

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

    // ================= Search =================

    // Search by Order ID
    @GetMapping("/search/order")
    public List<DeliveryTrackingResponseDTO> searchByOrderId(@RequestParam Long orderId) {
        return deliveryTrackingService.searchByOrderId(orderId);
    }

    // Search by Patient Name
    @GetMapping("/search/patient-name")
    public List<DeliveryTrackingResponseDTO> searchByPatientName(@RequestParam String fullName) {
        return deliveryTrackingService.searchByPatientName(fullName);
    }

    // Search by Patient Email
    @GetMapping("/search/patient-email")
    public List<DeliveryTrackingResponseDTO> searchByPatientEmail(@RequestParam String email) {
        return deliveryTrackingService.searchByPatientEmail(email);
    }

    // Search by Delivery Partner Name
    @GetMapping("/search/delivery-partner-name")
    public List<DeliveryTrackingResponseDTO> searchByDeliveryPartnerName(@RequestParam String fullName) {
        return deliveryTrackingService.searchByDeliveryPartnerName(fullName);
    }

    // Search by Delivery Partner Email
    @GetMapping("/search/delivery-partner-email")
    public List<DeliveryTrackingResponseDTO> searchByDeliveryPartnerEmail(@RequestParam String email) {
        return deliveryTrackingService.searchByDeliveryPartnerEmail(email);
    }

    // Search by Vehicle Number
    @GetMapping("/search/vehicle")
    public List<DeliveryTrackingResponseDTO> searchByVehicleNumber(@RequestParam String vehicleNumber) {
        return deliveryTrackingService.searchByVehicleNumber(vehicleNumber);
    }

    // Search by Availability Status
    @GetMapping("/search/availability")
    public List<DeliveryTrackingResponseDTO> searchByAvailabilityStatus(@RequestParam String availabilityStatus) {
        return deliveryTrackingService.searchByAvailabilityStatus(availabilityStatus);
    }

    // Search by Delivery Status
    @GetMapping("/search/status")
    public List<DeliveryTrackingResponseDTO> searchByStatus(@RequestParam DeliveryStatus status) {
        return deliveryTrackingService.searchByStatus(status);
    }

    // Search by Assigned Date Range
    @GetMapping("/search/assigned-date")
    public List<DeliveryTrackingResponseDTO> searchByAssignedDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {

        return deliveryTrackingService.searchByAssignedDate(startDate, endDate);
    }

    // Search by Dispatched Date Range
    @GetMapping("/search/dispatched-date")
    public List<DeliveryTrackingResponseDTO> searchByDispatchedDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {

        return deliveryTrackingService.searchByDispatchedDate(startDate, endDate);
    }

    // Search by Delivered Date Range
    @GetMapping("/search/delivered-date")
    public List<DeliveryTrackingResponseDTO> searchByDeliveredDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {

        return deliveryTrackingService.searchByDeliveredDate(startDate, endDate);
    }

    // Search by Remarks
    @GetMapping("/search/remarks")
    public List<DeliveryTrackingResponseDTO> searchByRemarks(@RequestParam String remarks) {
        return deliveryTrackingService.searchByRemarks(remarks);
    }
}
