package com.arogyamed.service.impl;

import com.arogyamed.dto.LoginRequestDTO;
import com.arogyamed.dto.LoginResponseDTO;
import com.arogyamed.dto.RegisterRequestDTO;
import com.arogyamed.dto.RegisterResponseDTO;
import com.arogyamed.model.User;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.security.JwtService;
import com.arogyamed.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhoneNumber(request.getPhoneNumber());

        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        return new RegisterResponseDTO(savedUser.getId(), savedUser.getFullName(), savedUser.getEmail(), "Registration Successful");

    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );


        String token = jwtService.generateToken(user.getEmail());


        return new LoginResponseDTO(
                token,
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                "Login Successful"
        );

    }

}
