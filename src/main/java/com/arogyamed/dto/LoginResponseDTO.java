package com.arogyamed.dto;

import com.arogyamed.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String token;

    private Long userId;

    private String fullName;

    private String email;

    private Role role;

    private String message;
}
