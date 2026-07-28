package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.UserRequestDTO;
import com.arogyamed.healthcare.dto.UserResponseDTO;
import com.arogyamed.healthcare.model.Role;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // REGISTER USER
    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {

        // check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword()); // later BCrypt
        user.setAddress(request.getAddress());
        user.setRole(request.getRole());
        user.setVerified(false);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    // GET USER BY ID
    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    // GET ALL USERS
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // DELETE USER
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // ================= Search =================

    @Override
    public List<UserResponseDTO> searchByFullName(String fullName) {

        return userRepository.findByFullNameContainingIgnoreCase(fullName)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> searchByEmail(String email) {

        return userRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> searchByPhoneNumber(String phoneNumber) {

        return userRepository.findByPhoneNumberContaining(phoneNumber)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> searchByRole(Role role) {

        return userRepository.findByRole(role)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> searchByVerified(boolean verified) {

        return userRepository.findByVerified(verified)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // MAPPER METHOD
    private UserResponseDTO mapToResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
        dto.setVerified(user.isVerified());
        return dto;
    }
}
