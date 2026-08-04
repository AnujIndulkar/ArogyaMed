package com.arogyamed.controller;

import com.arogyamed.dto.LoginRequestDTO;
import com.arogyamed.dto.LoginResponseDTO;
import com.arogyamed.dto.RegisterRequestDTO;
import com.arogyamed.dto.RegisterResponseDTO;
import com.arogyamed.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Register New User
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {

        System.out.println("REGISTER API CALLED");

        RegisterResponseDTO response = authService.register(request);

        System.out.println(response);

        return ResponseEntity.ok(response);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(authService.login(request));
    }




}
