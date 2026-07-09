package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.OrderRequestDTO;
import com.arogyamed.healthcare.dto.OrderResponseDTO;
import com.arogyamed.healthcare.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public OrderResponseDTO updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(id, request);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}