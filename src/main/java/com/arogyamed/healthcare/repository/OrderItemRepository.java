package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
