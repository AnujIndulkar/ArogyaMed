package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Payment;
import com.arogyamed.healthcare.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    long countByPaymentStatus(PaymentStatus paymentStatus);

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.paymentStatus='SUCCESS'")
    Double getTotalRevenue();
}
