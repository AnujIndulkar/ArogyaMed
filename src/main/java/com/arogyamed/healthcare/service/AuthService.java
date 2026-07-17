package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.LoginRequestDTO;
import com.arogyamed.healthcare.dto.LoginResponseDTO;
import com.arogyamed.healthcare.dto.RegisterRequestDTO;
import com.arogyamed.healthcare.dto.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

}
