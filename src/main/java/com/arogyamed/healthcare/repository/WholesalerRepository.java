package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Wholesaler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WholesalerRepository extends JpaRepository<Wholesaler, Long> {

    Optional<Wholesaler> findByUserId(Long userId);
}
