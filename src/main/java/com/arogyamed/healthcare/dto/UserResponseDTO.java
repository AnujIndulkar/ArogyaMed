package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.Role;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private Role role;

    private boolean verified;
}
