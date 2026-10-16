package com.arogyamed.repository;

import com.arogyamed.model.Order;
import com.arogyamed.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByStatus(OrderStatus status);

    // All orders of one patient, newest first (for the patient's "My orders" page)
    List<Order> findByPatient_IdOrderByOrderDateDesc(Long patientId);

    // Orders nobody has accepted yet (pharmacist "Available" tab)
    List<Order> findByPharmacistIsNullAndStatusOrderByOrderDateDesc(OrderStatus status);

    // Orders accepted by one pharmacist (pharmacist "My orders" tab)
    List<Order> findByPharmacist_IdOrderByOrderDateDesc(Long pharmacistId);

    // ================= Search =================

    List<Order> findByPatient_User_FullNameContainingIgnoreCase(String fullName);

    List<Order> findByPatient_User_EmailContainingIgnoreCase(String email);

    List<Order> findByPharmacist_User_FullNameContainingIgnoreCase(String fullName);

    List<Order> findByPharmacist_User_EmailContainingIgnoreCase(String email);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByTotalAmount(Double totalAmount);

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}