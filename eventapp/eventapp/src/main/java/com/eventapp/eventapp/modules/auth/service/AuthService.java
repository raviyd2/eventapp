package com.eventapp.eventapp.modules.auth.service;

import com.eventapp.eventapp.modules.auth.dto.AuthRequest;
import com.eventapp.eventapp.modules.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
    AuthResponse refreshToken(String refreshToken);
}