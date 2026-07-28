package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Order;
import com.arogyamed.healthcare.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByStatus(OrderStatus status);

    // ================= Search =================

    // Search by Patient Name
    List<Order> findByPatient_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Patient Email
    List<Order> findByPatient_User_EmailContainingIgnoreCase(String email);

    // Search by Pharmacist Name
    List<Order> findByPharmacist_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Pharmacist Email
    List<Order> findByPharmacist_User_EmailContainingIgnoreCase(String email);

    // Search by Status
    List<Order> findByStatus(OrderStatus status);

    // Search by Total Amount
    List<Order> findByTotalAmount(Double totalAmount);

    // Search by Order Date
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
