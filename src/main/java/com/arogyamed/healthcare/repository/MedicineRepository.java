package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
