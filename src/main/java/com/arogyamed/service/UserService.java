package com.arogyamed.service;

import com.arogyamed.dto.UserRequestDTO;
import com.arogyamed.dto.UserResponseDTO;
import com.arogyamed.model.Role;
import org.springframework.web.multipart.MultipartFile;

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

    // ================= Profile Picture =================

    UserResponseDTO uploadProfilePicture(Long userId, MultipartFile file);
}