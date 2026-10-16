package com.arogyamed.service;

import com.arogyamed.dto.CheckoutRequestDTO;
import com.arogyamed.dto.CheckoutResponseDTO;
import com.arogyamed.model.OrderStatus;

import java.util.List;

public interface CheckoutService {

    CheckoutResponseDTO checkout(String loginName, CheckoutRequestDTO request);

    CheckoutResponseDTO confirmPayment(String loginName, Long orderId, boolean simulateFailure);

    List<CheckoutResponseDTO> myOrders(String loginName);

    CheckoutResponseDTO cancelOrder(String loginName, Long orderId);

    // Pharmacist: orders nobody has accepted yet
    List<CheckoutResponseDTO> availableOrders();

    // Pharmacist: orders I accepted
    List<CheckoutResponseDTO> assignedOrders(String loginName);

    // Pharmacist: take an available order
    CheckoutResponseDTO acceptOrder(String loginName, Long orderId);

    // Pharmacist (own orders only) or admin (any order)
    CheckoutResponseDTO updateStatus(String loginName, boolean admin, Long orderId, OrderStatus newStatus);
}