package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
