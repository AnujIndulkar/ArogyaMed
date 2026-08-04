package com.arogyamed.service;

import com.arogyamed.dto.LoginRequestDTO;
import com.arogyamed.dto.LoginResponseDTO;
import com.arogyamed.dto.RegisterRequestDTO;
import com.arogyamed.dto.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

}
