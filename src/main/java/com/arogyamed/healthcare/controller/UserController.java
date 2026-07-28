package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.UserRequestDTO;
import com.arogyamed.healthcare.dto.UserResponseDTO;
import com.arogyamed.healthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.healthcare.model.Role;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE USER (REGISTER)
    @PostMapping
    public UserResponseDTO registerUser(@RequestBody UserRequestDTO request) {
        return userService.registerUser(request);
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // GET ALL USERS
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<UserResponseDTO>> searchByFullName(@RequestParam String fullName) {

        return ResponseEntity.ok(userService.searchByFullName(fullName));
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserResponseDTO>> searchByEmail(@RequestParam String email) {

        return ResponseEntity.ok(userService.searchByEmail(email));
    }

    @GetMapping("/search/phone")
    public ResponseEntity<List<UserResponseDTO>> searchByPhoneNumber(@RequestParam String phoneNumber) {

        return ResponseEntity.ok(userService.searchByPhoneNumber(phoneNumber));
    }

    @GetMapping("/search/role")
    public ResponseEntity<List<UserResponseDTO>> searchByRole(@RequestParam Role role) {

        return ResponseEntity.ok(userService.searchByRole(role));
    }

    @GetMapping("/search/verified")
    public ResponseEntity<List<UserResponseDTO>> searchByVerified(@RequestParam boolean verified) {

        return ResponseEntity.ok(userService.searchByVerified(verified));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

}
