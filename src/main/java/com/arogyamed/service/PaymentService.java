package com.arogyamed.service;

import com.arogyamed.dto.PaymentRequestDTO;
import com.arogyamed.dto.PaymentResponseDTO;
import com.arogyamed.model.PaymentMethod;
import com.arogyamed.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {

    PaymentResponseDTO createPayment(PaymentRequestDTO request);

    PaymentResponseDTO getPaymentById(Long id);

    PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO request);

    List<PaymentResponseDTO> getAllPayments();

    // ================= Search =================

    List<PaymentResponseDTO> searchByOrderId(Long orderId);

    List<PaymentResponseDTO> searchByPatientName(String fullName);

    List<PaymentResponseDTO> searchByPatientEmail(String email);

    List<PaymentResponseDTO> searchByPharmacistName(String fullName);

    List<PaymentResponseDTO> searchByPaymentStatus(PaymentStatus paymentStatus);

    List<PaymentResponseDTO> searchByPaymentMethod(PaymentMethod paymentMethod);

    List<PaymentResponseDTO> searchByTransactionId(String transactionId);

    List<PaymentResponseDTO> searchByAmount(Double amount);

    List<PaymentResponseDTO> searchByPaymentDate(LocalDateTime startDate, LocalDateTime endDate);

}
