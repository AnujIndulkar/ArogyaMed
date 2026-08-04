package com.arogyamed.service.impl;

import com.arogyamed.dto.OrderRequestDTO;
import com.arogyamed.dto.OrderResponseDTO;
import com.arogyamed.model.Order;
import com.arogyamed.model.OrderStatus;
import com.arogyamed.model.Patient;
import com.arogyamed.model.Pharmacist;
import com.arogyamed.repository.OrderRepository;
import com.arogyamed.repository.PatientRepository;
import com.arogyamed.repository.PharmacistRepository;
import com.arogyamed.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        Pharmacist pharmacist = pharmacistRepository.findById(request.getPharmacistId()).orElseThrow(() ->
                        new RuntimeException("Pharmacist not found"));

        Order order = new Order();

        order.setPatient(patient);

        order.setPharmacist(pharmacist);

        order.setTotalAmount(request.getTotalAmount());

        // Default Status
        if (request.getStatus() == null) {

            order.setStatus(OrderStatus.PENDING);

        } else {

            order.setStatus(request.getStatus());
        }

        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return mapToDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order not found"));

        return mapToDTO(order);
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO request) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setTotalAmount(request.getTotalAmount());

        order.setStatus(request.getStatus());

        Order updatedOrder = orderRepository.save(order);

        return mapToDTO(updatedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToDTO(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(order.getId());

        dto.setPatientId(order.getPatient().getId());

        dto.setPatientName(order.getPatient().getUser().getFullName());

        dto.setPharmacistId(order.getPharmacist().getId());

        dto.setPharmacistName(order.getPharmacist().getUser().getFullName());

        dto.setTotalAmount(order.getTotalAmount());

        dto.setStatus(order.getStatus());

        dto.setOrderDate(order.getOrderDate());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<OrderResponseDTO> searchByPatientName(String fullName) {

        return mapToDTOList(
                orderRepository.findByPatient_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<OrderResponseDTO> searchByPatientEmail(String email) {

        return mapToDTOList(orderRepository.findByPatient_User_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<OrderResponseDTO> searchByPharmacistName(String fullName) {

        return mapToDTOList(orderRepository.findByPharmacist_User_FullNameContainingIgnoreCase(fullName));
    }

    @Override
    public List<OrderResponseDTO> searchByPharmacistEmail(String email) {

        return mapToDTOList(orderRepository.findByPharmacist_User_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<OrderResponseDTO> searchByStatus(OrderStatus status) {

        return mapToDTOList(orderRepository.findByStatus(status));
    }

    @Override
    public List<OrderResponseDTO> searchByTotalAmount(Double totalAmount) {

        return mapToDTOList(orderRepository.findByTotalAmount(totalAmount));
    }

    @Override
    public List<OrderResponseDTO> searchByOrderDate(LocalDateTime startDate, LocalDateTime endDate) {

        return mapToDTOList(orderRepository.findByOrderDateBetween(startDate, endDate));
    }

    private List<OrderResponseDTO> mapToDTOList(List<Order> orders) {

        return orders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
