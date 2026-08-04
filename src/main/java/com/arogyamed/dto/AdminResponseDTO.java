package com.arogyamed.dto;

import com.arogyamed.model.AdminDepartment;
import com.arogyamed.model.AdminStatus;
import com.arogyamed.model.AdminType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminResponseDTO {

    private Long id;

    private Long userId;

    private String userName;

    private String email;

    private String employeeId;

    private AdminType adminType;

    private AdminDepartment department;

    private String designation;

    private String officeLocation;

    private LocalDate joiningDate;

    private AdminStatus status;

}
