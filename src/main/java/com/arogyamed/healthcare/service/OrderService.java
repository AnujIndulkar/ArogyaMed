package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.OrderRequestDTO;
import com.arogyamed.healthcare.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO request);

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO request);

    List<OrderResponseDTO> getAllOrders();

}
