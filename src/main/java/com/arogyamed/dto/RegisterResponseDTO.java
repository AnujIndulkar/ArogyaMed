package com.arogyamed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {

    private Long userId;

    private String fullName;

    private String email;

    private String message;
}