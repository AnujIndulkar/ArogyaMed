package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {

    Optional<DeliveryPartner> findByUserId(Long userId);
}
