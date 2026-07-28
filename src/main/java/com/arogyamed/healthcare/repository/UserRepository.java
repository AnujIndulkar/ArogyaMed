package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Role;
import com.arogyamed.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ================= Authentication =================

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    // ================= Search =================

    List<User> findByFullNameContainingIgnoreCase(String fullName);

    List<User> findByEmailContainingIgnoreCase(String email);

    List<User> findByPhoneNumberContaining(String phoneNumber);

    List<User> findByRole(Role role);

    List<User> findByVerified(boolean verified);
}
