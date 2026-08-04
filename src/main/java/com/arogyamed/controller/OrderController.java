package com.arogyamed.controller;

import com.arogyamed.dto.OrderRequestDTO;
import com.arogyamed.dto.OrderResponseDTO;
import com.arogyamed.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.model.OrderStatus;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public OrderResponseDTO updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(id, request);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ================= Search =================

    // Search by Patient Name
    @GetMapping("/search/patient-name")
    public List<OrderResponseDTO> searchByPatientName(@RequestParam String fullName) {
        return orderService.searchByPatientName(fullName);
    }

    // Search by Patient Email
    @GetMapping("/search/patient-email")
    public List<OrderResponseDTO> searchByPatientEmail(@RequestParam String email) {
        return orderService.searchByPatientEmail(email);
    }

    // Search by Pharmacist Name
    @GetMapping("/search/pharmacist-name")
    public List<OrderResponseDTO> searchByPharmacistName(@RequestParam String fullName) {
        return orderService.searchByPharmacistName(fullName);
    }

    // Search by Pharmacist Email
    @GetMapping("/search/pharmacist-email")
    public List<OrderResponseDTO> searchByPharmacistEmail(@RequestParam String email) {
        return orderService.searchByPharmacistEmail(email);
    }

    // Search by Status
    @GetMapping("/search/status")
    public List<OrderResponseDTO> searchByStatus(@RequestParam OrderStatus status) {
        return orderService.searchByStatus(status);
    }

    // Search by Total Amount
    @GetMapping("/search/amount")
    public List<OrderResponseDTO> searchByTotalAmount(@RequestParam Double totalAmount) {
        return orderService.searchByTotalAmount(totalAmount);
    }

    // Search by Order Date Range
    @GetMapping("/search/date")
    public List<OrderResponseDTO> searchByOrderDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {

        return orderService.searchByOrderDate(startDate, endDate);
    }
}