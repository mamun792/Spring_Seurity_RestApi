package com.example.jwt_security.service;

import com.example.jwt_security.dto.JwtAuthencationResponce;
import com.example.jwt_security.dto.SinginRequest;
import com.example.jwt_security.dto.SingupRequest;
import com.example.jwt_security.entity.User;

public interface AuthecationService {
    public User singup(SingupRequest singupRequest);

    JwtAuthencationResponce signin(SinginRequest singinRequest);
}