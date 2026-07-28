package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.UserRequestDTO;
import com.arogyamed.healthcare.dto.UserResponseDTO;
import com.arogyamed.healthcare.model.Role;

import java.util.List;

public interface UserService {

    // ================= CRUD =================

    UserResponseDTO registerUser(UserRequestDTO request);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    void deleteUser(Long id);

    // ================= Search =================

    List<UserResponseDTO> searchByFullName(String fullName);

    List<UserResponseDTO> searchByEmail(String email);

    List<UserResponseDTO> searchByPhoneNumber(String phoneNumber);

    List<UserResponseDTO> searchByRole(Role role);

    List<UserResponseDTO> searchByVerified(boolean verified);
}
