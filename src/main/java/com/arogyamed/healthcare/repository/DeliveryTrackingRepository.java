package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.DeliveryStatus;
import com.arogyamed.healthcare.model.DeliveryTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, Long> {

    long countByStatus(DeliveryStatus status);

}
