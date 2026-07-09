package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.OrderItemRequestDTO;
import com.arogyamed.healthcare.dto.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {

    OrderItemResponseDTO createOrderItem(OrderItemRequestDTO request);

    OrderItemResponseDTO getOrderItemById(Long id);

    OrderItemResponseDTO updateOrderItem(Long id, OrderItemRequestDTO request);

    List<OrderItemResponseDTO> getAllOrderItems();

}
