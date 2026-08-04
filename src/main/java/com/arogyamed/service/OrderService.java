package com.arogyamed.service;

import com.arogyamed.dto.OrderRequestDTO;
import com.arogyamed.dto.OrderResponseDTO;
import com.arogyamed.model.OrderStatus;

import java.time.LocalDateTime;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO request);

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO request);

    List<OrderResponseDTO> getAllOrders();

    // ================= Search =================

    List<OrderResponseDTO> searchByPatientName(String fullName);

    List<OrderResponseDTO> searchByPatientEmail(String email);

    List<OrderResponseDTO> searchByPharmacistName(String fullName);

    List<OrderResponseDTO> searchByPharmacistEmail(String email);

    List<OrderResponseDTO> searchByStatus(OrderStatus status);

    List<OrderResponseDTO> searchByTotalAmount(Double totalAmount);

    List<OrderResponseDTO> searchByOrderDate(LocalDateTime startDate,LocalDateTime endDate);

}
