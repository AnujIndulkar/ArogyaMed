package com.arogyamed.controller;

import com.arogyamed.dto.OrderItemRequestDTO;
import com.arogyamed.dto.OrderItemResponseDTO;
import com.arogyamed.service.OrderItemService;
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

    // ================= Search =================

    // Search by Order ID
    @GetMapping("/search/order")
    public List<OrderItemResponseDTO> searchByOrderId(@RequestParam Long orderId) {
        return orderItemService.searchByOrderId(orderId);
    }

    // Search by Medicine Name
    @GetMapping("/search/medicine")
    public List<OrderItemResponseDTO> searchByMedicineName(@RequestParam String medicineName) {
        return orderItemService.searchByMedicineName(medicineName);
    }

    // Search by Generic Name
    @GetMapping("/search/generic")
    public List<OrderItemResponseDTO> searchByGenericName(@RequestParam String genericName) {
        return orderItemService.searchByGenericName(genericName);
    }

    // Search by Quantity
    @GetMapping("/search/quantity")
    public List<OrderItemResponseDTO> searchByQuantity(@RequestParam Integer quantity) {
        return orderItemService.searchByQuantity(quantity);
    }

    // Search by Price
    @GetMapping("/search/price")
    public List<OrderItemResponseDTO> searchByPrice(@RequestParam Double price) {
        return orderItemService.searchByPrice(price);
    }

    // Search by Subtotal
    @GetMapping("/search/subtotal")
    public List<OrderItemResponseDTO> searchBySubtotal(@RequestParam Double subtotal) {
        return orderItemService.searchBySubtotal(subtotal);
    }
}
