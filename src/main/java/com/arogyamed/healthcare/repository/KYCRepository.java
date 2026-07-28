package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.KYC;
import com.arogyamed.healthcare.model.KYCStatus;
import com.arogyamed.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, Long> {

    Optional<KYC> findByUser(User user);

    // ================= Search =================

    List<KYC> findByUser_FullNameContainingIgnoreCase(String fullName);

    List<KYC> findByUser_EmailContainingIgnoreCase(String email);

    List<KYC> findByDocumentTypeContainingIgnoreCase(String documentType);

    List<KYC> findByDocumentNumberContainingIgnoreCase(String documentNumber);

    List<KYC> findByStatus(KYCStatus status);

    List<KYC> findByRemarksContainingIgnoreCase(String remarks);
}
