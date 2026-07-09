package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.AdminDepartment;
import com.arogyamed.healthcare.model.AdminStatus;
import com.arogyamed.healthcare.model.AdminType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminRequestDTO {

    private Long userId;

    private AdminType adminType;

    private AdminDepartment department;

    private String designation;

    private String officeLocation;

    private LocalDate joiningDate;

    private AdminStatus status;

}
