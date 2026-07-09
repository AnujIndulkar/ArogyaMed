package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
