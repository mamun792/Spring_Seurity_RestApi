package com.example.jwt_security.dto;

import lombok.Data;

@Data
public class JwtAuthencationResponce {
    private String token;
    private String RefeshToken;

}