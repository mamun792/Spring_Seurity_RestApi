package com.example.jwt_security.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String extractUsername(String token);

    public String generateToken(UserDetails userDetails);

    public boolean validateToken(String token, UserDetails userDetails);

    public String generateRefershToken(Map<String, Object> extractClaim, UserDetails userDetails);
}