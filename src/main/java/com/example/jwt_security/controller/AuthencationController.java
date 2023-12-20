package com.example.jwt_security.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.jwt_security.entity.User;

import com.example.jwt_security.service.AuthecationService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jwt_security.dto.JwtAuthencationResponce;
import com.example.jwt_security.dto.SinginRequest;
import com.example.jwt_security.dto.SingupRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthencationController {
    private final AuthecationService authecationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SingupRequest singupRequest) {

        return ResponseEntity.ok(authecationService.singup(singupRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthencationResponce> signin(@RequestBody SinginRequest singupRequest) {

        return ResponseEntity.ok(authecationService.signin(singupRequest));
    }
}