package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Order;
import com.arogyamed.healthcare.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByStatus(OrderStatus status);
}
