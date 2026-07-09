package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
