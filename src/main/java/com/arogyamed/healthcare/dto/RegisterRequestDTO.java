package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String fullName;

    private String email;

    private String password;

    private String phoneNumber;

    private Role role;
}