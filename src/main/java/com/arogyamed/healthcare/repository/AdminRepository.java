package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Admin;
import com.arogyamed.healthcare.model.AdminDepartment;
import com.arogyamed.healthcare.model.AdminStatus;
import com.arogyamed.healthcare.model.AdminType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmployeeId(String employeeId);

    List<Admin> findByDepartment(AdminDepartment department);

    List<Admin> findByAdminType(AdminType adminType);

    List<Admin> findByStatus(AdminStatus status);

    boolean existsByEmployeeId(String employeeId);

    // ==========================
    // Search & Filtering
    // ==========================

    List<Admin> findByDesignationContainingIgnoreCase(String designation);

    List<Admin> findByOfficeLocationContainingIgnoreCase(String officeLocation);

    List<Admin> findByDepartmentAndStatus(AdminDepartment department, AdminStatus status);

    List<Admin> findByAdminTypeAndStatus(AdminType adminType, AdminStatus status);

    List<Admin> findByJoiningDate(LocalDate joiningDate);



}
