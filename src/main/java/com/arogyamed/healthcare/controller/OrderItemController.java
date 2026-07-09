package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.OrderItemRequestDTO;
import com.arogyamed.healthcare.dto.OrderItemResponseDTO;
import com.arogyamed.healthcare.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public OrderItemResponseDTO createOrderItem(@RequestBody OrderItemRequestDTO request) {
        return orderItemService.createOrderItem(request);
    }

    @GetMapping("/{id}")
    public OrderItemResponseDTO getOrderItemById(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

    @PutMapping("/{id}")
    public OrderItemResponseDTO updateOrderItem(@PathVariable Long id, @RequestBody OrderItemRequestDTO request) {
        return orderItemService.updateOrderItem(id, request);
    }

    @GetMapping
    public List<OrderItemResponseDTO> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }
}
