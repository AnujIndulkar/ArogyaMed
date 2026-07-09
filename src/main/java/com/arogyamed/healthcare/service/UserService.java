package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.UserRequestDTO;
import com.arogyamed.healthcare.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO request);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    void deleteUser(Long id);
}
