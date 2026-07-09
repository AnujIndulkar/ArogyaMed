package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.KYC;
import com.arogyamed.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, Long> {

    Optional<KYC> findByUser(User user);

}
