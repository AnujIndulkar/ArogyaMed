package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.OrderItemRequestDTO;
import com.arogyamed.healthcare.dto.OrderItemResponseDTO;
import com.arogyamed.healthcare.model.Medicine;
import com.arogyamed.healthcare.model.Order;
import com.arogyamed.healthcare.model.OrderItem;
import com.arogyamed.healthcare.repository.MedicineRepository;
import com.arogyamed.healthcare.repository.OrderItemRepository;
import com.arogyamed.healthcare.repository.OrderRepository;
import com.arogyamed.healthcare.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO request) {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                        new RuntimeException("Order not found"));

        Medicine medicine = medicineRepository.findById(request.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);

        orderItem.setMedicine(medicine);

        orderItem.setQuantity(request.getQuantity());

        orderItem.setPrice(medicine.getPrice());

        orderItem.setSubtotal(medicine.getPrice() * request.getQuantity());

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        return mapToDTO(savedOrderItem);
    }

    @Override
    public OrderItemResponseDTO getOrderItemById(Long id) {

        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order Item not found"));

        return mapToDTO(orderItem);
    }

    @Override
    public OrderItemResponseDTO updateOrderItem(Long id, OrderItemRequestDTO request) {

        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order Item not found"));

        Medicine medicine = medicineRepository.findById(request.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        orderItem.setMedicine(medicine);

        orderItem.setQuantity(request.getQuantity());

        orderItem.setPrice(medicine.getPrice());

        orderItem.setSubtotal(medicine.getPrice() * request.getQuantity());

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);

        return mapToDTO(updatedOrderItem);
    }

    @Override
    public List<OrderItemResponseDTO> getAllOrderItems() {

        return orderItemRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderItemResponseDTO mapToDTO(OrderItem orderItem) {

        OrderItemResponseDTO dto = new OrderItemResponseDTO();

        dto.setId(orderItem.getId());

        dto.setOrderId(orderItem.getOrder().getId());

        dto.setMedicineId(orderItem.getMedicine().getId());

        dto.setMedicineName(orderItem.getMedicine().getMedicineName());

        dto.setQuantity(orderItem.getQuantity());

        dto.setPrice(orderItem.getPrice());

        dto.setSubtotal(orderItem.getSubtotal());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<OrderItemResponseDTO> searchByOrderId(Long orderId) {

        return mapToDTOList(orderItemRepository.findByOrderId(orderId));
    }

    @Override
    public List<OrderItemResponseDTO> searchByMedicineName(String medicineName) {

        return mapToDTOList(orderItemRepository.findByMedicineMedicineNameContainingIgnoreCase(medicineName));
    }

    @Override
    public List<OrderItemResponseDTO> searchByGenericName(String genericName) {

        return mapToDTOList(orderItemRepository.findByMedicineGenericNameContainingIgnoreCase(genericName));
    }

    @Override
    public List<OrderItemResponseDTO> searchByQuantity(Integer quantity) {

        return mapToDTOList(orderItemRepository.findByQuantity(quantity));
    }

    @Override
    public List<OrderItemResponseDTO> searchByPrice(Double price) {

        return mapToDTOList(orderItemRepository.findByPrice(price));
    }

    @Override
    public List<OrderItemResponseDTO> searchBySubtotal(Double subtotal) {

        return mapToDTOList(orderItemRepository.findBySubtotal(subtotal));
    }

    private List<OrderItemResponseDTO> mapToDTOList(List<OrderItem> orderItems) {

        return orderItems.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
