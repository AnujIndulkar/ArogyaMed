package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

    Optional<Pharmacist> findByUserId(Long userId);
}
