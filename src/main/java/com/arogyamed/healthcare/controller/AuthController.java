package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.LoginRequestDTO;
import com.arogyamed.healthcare.dto.LoginResponseDTO;
import com.arogyamed.healthcare.dto.RegisterRequestDTO;
import com.arogyamed.healthcare.dto.RegisterResponseDTO;
import com.arogyamed.healthcare.service.AuthService;
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
    public ResponseEntity<RegisterResponseDTO> register(
            @RequestBody RegisterRequestDTO request) {

        System.out.println("REGISTER API CALLED");

        RegisterResponseDTO response = authService.register(request);

        System.out.println(response);

        return ResponseEntity.ok(response);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(authService.login(request));
    }


}
