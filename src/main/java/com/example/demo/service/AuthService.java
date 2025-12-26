package com.example.demo.service;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.RegisterRequestDto;

public interface AuthService {
    String login(AuthRequestDto dto);
    void register(RegisterRequestDto dto);
}
