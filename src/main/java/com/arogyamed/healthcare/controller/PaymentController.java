package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.PaymentRequestDTO;
import com.arogyamed.healthcare.dto.PaymentResponseDTO;
import com.arogyamed.healthcare.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
