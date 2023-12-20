package com.example.jwt_security.config;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.jwt_security.service.JwtService;
import com.example.jwt_security.service.UserService;

import java.io.IOException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
@RequiredArgsConstructor
public class JwtAuthencationfillter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwttoken;
        final String userEmail;
        if (StringUtils.isEmpty(authorizationHeader)
                || !org.apache.commons.lang3.StringUtils.startsWith(authorizationHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwttoken = authorizationHeader.substring(7);
        userEmail = jwtService.extractUsername(jwttoken);
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {

            final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            if (jwtService.validateToken(jwttoken, userDetails)) {
                SecurityContext secondContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                secondContext.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(secondContext);
            }

        }
        filterChain.doFilter(request, response);
    }
}