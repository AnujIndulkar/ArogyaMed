package com.arogyamed.dto;

import com.arogyamed.model.Role;
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
