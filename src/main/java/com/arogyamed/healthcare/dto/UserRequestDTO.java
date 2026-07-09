package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.Role;
import lombok.Data;

@Data
public class UserRequestDTO {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String password;

    private String address;

    private Role role;
}
