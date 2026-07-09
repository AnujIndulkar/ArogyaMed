package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.PaymentRequestDTO;
import com.arogyamed.healthcare.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    PaymentResponseDTO createPayment(PaymentRequestDTO request);

    PaymentResponseDTO getPaymentById(Long id);

    PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO request);

    List<PaymentResponseDTO> getAllPayments();

}
