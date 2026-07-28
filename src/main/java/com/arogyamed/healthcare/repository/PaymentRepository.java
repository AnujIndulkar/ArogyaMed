package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Payment;
import com.arogyamed.healthcare.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.arogyamed.healthcare.model.PaymentMethod;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    long countByPaymentStatus(PaymentStatus paymentStatus);

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.paymentStatus='SUCCESS'")
    Double getTotalRevenue();

    // ================= Search =================

    // Search by Order ID
    List<Payment> findByOrderId(Long orderId);

    // Search by Patient Name
    List<Payment> findByOrder_Patient_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Patient Email
    List<Payment> findByOrder_Patient_User_EmailContainingIgnoreCase(String email);

    // Search by Pharmacist Name
    List<Payment> findByOrder_Pharmacist_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Payment Status
    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    // Search by Payment Method
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    // Search by Transaction ID
    List<Payment> findByTransactionIdContainingIgnoreCase(String transactionId);

    // Search by Amount
    List<Payment> findByAmount(Double amount);

    // Search by Payment Date
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
