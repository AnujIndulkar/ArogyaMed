package com.arogyamed.controller;

import com.arogyamed.dto.PaymentRequestDTO;
import com.arogyamed.dto.PaymentResponseDTO;
import com.arogyamed.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.model.PaymentMethod;
import com.arogyamed.model.PaymentStatus;

import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public PaymentResponseDTO createPayment(@RequestBody PaymentRequestDTO request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PutMapping("/{id}")
    public PaymentResponseDTO updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDTO request) {
        return paymentService.updatePayment(id, request);
    }

    @GetMapping
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // ================= Search =================

    // Search by Order ID
    @GetMapping("/search/order/{orderId}")
    public List<PaymentResponseDTO> searchByOrderId(@PathVariable Long orderId) {
        return paymentService.searchByOrderId(orderId);
    }

    // Search by Patient Name
    @GetMapping("/search/patient")
    public List<PaymentResponseDTO> searchByPatientName(@RequestParam String fullName) {
        return paymentService.searchByPatientName(fullName);
    }

    // Search by Patient Email
    @GetMapping("/search/patient-email")
    public List<PaymentResponseDTO> searchByPatientEmail(@RequestParam String email) {
        return paymentService.searchByPatientEmail(email);
    }

    // Search by Pharmacist Name
    @GetMapping("/search/pharmacist")
    public List<PaymentResponseDTO> searchByPharmacistName(@RequestParam String fullName) {
        return paymentService.searchByPharmacistName(fullName);
    }

    // Search by Payment Status
    @GetMapping("/search/status")
    public List<PaymentResponseDTO> searchByPaymentStatus(@RequestParam PaymentStatus paymentStatus) {
        return paymentService.searchByPaymentStatus(paymentStatus);
    }

    // Search by Payment Method
    @GetMapping("/search/method")
    public List<PaymentResponseDTO> searchByPaymentMethod(@RequestParam PaymentMethod paymentMethod) {
        return paymentService.searchByPaymentMethod(paymentMethod);
    }

    // Search by Transaction ID
    @GetMapping("/search/transaction")
    public List<PaymentResponseDTO> searchByTransactionId(@RequestParam String transactionId) {
        return paymentService.searchByTransactionId(transactionId);
    }

    // Search by Amount
    @GetMapping("/search/amount")
    public List<PaymentResponseDTO> searchByAmount(@RequestParam Double amount) {
        return paymentService.searchByAmount(amount);
    }

    // Search by Payment Date
    @GetMapping("/search/date")
    public List<PaymentResponseDTO> searchByPaymentDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return paymentService.searchByPaymentDate(startDate, endDate);
    }
}
