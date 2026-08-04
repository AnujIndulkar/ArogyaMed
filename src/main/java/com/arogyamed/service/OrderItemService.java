package com.arogyamed.service;

import com.arogyamed.dto.OrderItemRequestDTO;
import com.arogyamed.dto.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {

    OrderItemResponseDTO createOrderItem(OrderItemRequestDTO request);

    OrderItemResponseDTO getOrderItemById(Long id);

    OrderItemResponseDTO updateOrderItem(Long id, OrderItemRequestDTO request);

    List<OrderItemResponseDTO> getAllOrderItems();

    // ================= Search =================

    List<OrderItemResponseDTO> searchByOrderId(Long orderId);

    List<OrderItemResponseDTO> searchByMedicineName(String medicineName);

    List<OrderItemResponseDTO> searchByGenericName(String genericName);

    List<OrderItemResponseDTO> searchByQuantity(Integer quantity);

    List<OrderItemResponseDTO> searchByPrice(Double price);

    List<OrderItemResponseDTO> searchBySubtotal(Double subtotal);

}
