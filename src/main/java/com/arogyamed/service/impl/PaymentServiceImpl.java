package com.arogyamed.service.impl;

import com.arogyamed.dto.PaymentRequestDTO;
import com.arogyamed.dto.PaymentResponseDTO;
import com.arogyamed.model.Order;
import com.arogyamed.model.Payment;
import com.arogyamed.model.PaymentStatus;
import com.arogyamed.repository.OrderRepository;
import com.arogyamed.repository.PaymentRepository;
import com.arogyamed.service.PaymentService;
import com.arogyamed.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

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

    // ================= Search =================

    @Override
    public List<PaymentResponseDTO> searchByOrderId(Long orderId) {

        return mapToDTOList(paymentRepository.findByOrderId(orderId));
    }

    @Override
    public List<PaymentResponseDTO> searchByPatientName(String fullName) {

        return mapToDTOList(paymentRepository.findByOrder_Patient_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<PaymentResponseDTO> searchByPatientEmail(String email) {

        return mapToDTOList(paymentRepository.findByOrder_Patient_User_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<PaymentResponseDTO> searchByPharmacistName(String fullName) {

        return mapToDTOList(paymentRepository.findByOrder_Pharmacist_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<PaymentResponseDTO> searchByPaymentStatus(PaymentStatus paymentStatus) {

        return mapToDTOList(paymentRepository.findByPaymentStatus(paymentStatus));
    }

    @Override
    public List<PaymentResponseDTO> searchByPaymentMethod(PaymentMethod paymentMethod) {

        return mapToDTOList(paymentRepository.findByPaymentMethod(paymentMethod));
    }

    @Override
    public List<PaymentResponseDTO> searchByTransactionId(String transactionId) {

        return mapToDTOList(paymentRepository.findByTransactionIdContainingIgnoreCase(transactionId));
    }

    @Override
    public List<PaymentResponseDTO> searchByAmount(Double amount) {

        return mapToDTOList(paymentRepository.findByAmount(amount));
    }

    @Override
    public List<PaymentResponseDTO> searchByPaymentDate(LocalDateTime startDate, LocalDateTime endDate) {

        return mapToDTOList(paymentRepository.findByPaymentDateBetween(startDate, endDate));
    }

    private List<PaymentResponseDTO> mapToDTOList(List<Payment> payments) {

        return payments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
