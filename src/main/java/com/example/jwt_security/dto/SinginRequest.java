package com.example.jwt_security.dto;

import lombok.Data;

@Data
public class SinginRequest {
    private String email;
    private String password;
}