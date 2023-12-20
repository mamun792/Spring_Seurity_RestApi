package com.example.jwt_security.service;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt_security.dto.JwtAuthencationResponce;
import com.example.jwt_security.dto.SinginRequest;
import com.example.jwt_security.dto.SingupRequest;
import com.example.jwt_security.entity.Role;
import com.example.jwt_security.entity.User;
import com.example.jwt_security.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthecationServiceImpl implements AuthecationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User singup(SingupRequest singupRequest) {
        User user = new User();
        user.setFistname(singupRequest.getFistname());
        user.setLastname(singupRequest.getLastname());
        user.setEmail(singupRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(singupRequest.getPassword()));
        return userRepo.save(user);
    }

    // public JwtAuthencationResponce signin(SinginRequest singinRequest) {
    // authenticationManager.authenticate(new
    // UsernamePasswordAuthenticationToken(singinRequest.getEmail(),
    // singinRequest.getPassword()));

    // var user = userRepo.findByEmail(singinRequest.getEmail())
    // .orElseThrow(
    // () -> new UsernameNotFoundException("User not found with email: " +
    // singinRequest.getEmail()));
    // var jet = jwtService.generateToken(user);
    // var refershjwtToken = jwtService.generateRefershToken(new HashMap<>(), user);
    // JwtAuthencationResponce jwtAuthencationResponce = new
    // JwtAuthencationResponce();
    // jwtAuthencationResponce.setToken(jet);

    // jwtAuthencationResponce.setRefeshToken(refershjwtToken);

    // return jwtAuthencationResponce;
    // }
    public JwtAuthencationResponce signin(SinginRequest singinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singinRequest.getEmail(),
                singinRequest.getPassword()));

        var user = userRepo.findByEmail(singinRequest.getEmail())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + singinRequest.getEmail()));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefershToken(new HashMap<>(), user);

        JwtAuthencationResponce jwtAuthencationResponce = new JwtAuthencationResponce();
        jwtAuthencationResponce.setToken(jwtToken);
        jwtAuthencationResponce.setRefeshToken(refreshToken);

        return jwtAuthencationResponce;
    }
}