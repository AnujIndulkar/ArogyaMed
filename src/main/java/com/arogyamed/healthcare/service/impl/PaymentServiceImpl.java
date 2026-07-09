package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.PaymentRequestDTO;
import com.arogyamed.healthcare.dto.PaymentResponseDTO;
import com.arogyamed.healthcare.model.Order;
import com.arogyamed.healthcare.model.Payment;
import com.arogyamed.healthcare.model.PaymentStatus;
import com.arogyamed.healthcare.repository.OrderRepository;
import com.arogyamed.healthcare.repository.PaymentRepository;
import com.arogyamed.healthcare.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl
        implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                        new RuntimeException("Order not found"));

        Payment payment = new Payment();

        payment.setOrder(order);

        // Automatically read total amount from Order
        payment.setAmount(order.getTotalAmount());

        payment.setPaymentMethod(request.getPaymentMethod());

        // Default Status
        if (request.getPaymentStatus() == null) {

            payment.setPaymentStatus(PaymentStatus.PENDING);

        } else {

            payment.setPaymentStatus(request.getPaymentStatus());
        }

        // Generate Transaction ID
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // Save Current Date & Time
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        return mapToDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        return mapToDTO(payment);
    }

    @Override
    public PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO request) {

        Payment payment = paymentRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        payment.setPaymentMethod(request.getPaymentMethod());

        payment.setPaymentStatus(request.getPaymentStatus());

        Payment updatedPayment = paymentRepository.save(payment);

        return mapToDTO(updatedPayment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PaymentResponseDTO mapToDTO(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setId(payment.getId());

        dto.setOrderId(payment.getOrder().getId());

        dto.setAmount(payment.getAmount());

        dto.setPaymentMethod(payment.getPaymentMethod());

        dto.setPaymentStatus(payment.getPaymentStatus());

        dto.setTransactionId(payment.getTransactionId());

        dto.setPaymentDate(payment.getPaymentDate());

        return dto;
    }
}
