package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // ================= Search =================

    // Search by Order ID
    List<OrderItem> findByOrderId(Long orderId);

    // Search by Medicine Name
    List<OrderItem> findByMedicineMedicineNameContainingIgnoreCase(String medicineName);

    // Search by Generic Name
    List<OrderItem> findByMedicineGenericNameContainingIgnoreCase(String genericName);

    // Search by Quantity
    List<OrderItem> findByQuantity(Integer quantity);

    // Search by Price
    List<OrderItem> findByPrice(Double price);

    // Search by Subtotal
    List<OrderItem> findBySubtotal(Double subtotal);
}
