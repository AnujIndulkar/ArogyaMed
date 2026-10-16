package com.arogyamed.service.impl;

import com.arogyamed.dto.LoginRequestDTO;
import com.arogyamed.dto.LoginResponseDTO;
import com.arogyamed.dto.RegisterRequestDTO;
import com.arogyamed.dto.RegisterResponseDTO;
import com.arogyamed.model.Role;
import com.arogyamed.model.User;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.security.JwtService;
import com.arogyamed.service.AuthService;
import com.arogyamed.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final EmailVerificationService emailVerificationService;

    @Override
    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        // Nobody can make themselves an admin. Admin accounts are created by the
        // database seeder or directly in the database, never through public sign-up.
        if (request.getRole() == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Admin accounts cannot be created through registration");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // The email must have been verified with an OTP. Throws 403 if not.
        emailVerificationService.assertVerified(request.getEmail(), request.getEmailVerificationToken());

        User user = new User();

        user.setFullName(request.getFullName());

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhoneNumber(request.getPhoneNumber());

        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        // the proof is single-use: remove it now that the account exists
        emailVerificationService.clear(request.getEmail());

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