package com.example.jwt_security.service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;

@Service
public class JwtServiceImpl implements JwtService {
    // generateToken
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSiginKeys(), SignatureAlgorithm.HS256).compact();
    }

    public String generateRefershToken(Map<String, Object> extractClaim, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extractClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSiginKeys()).compact();

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // private Key getSiginKey() {
    // byte[] Key = Decoders.BASE64.decode("QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
    // return Keys.hmacShaKeyFor(Key);
    // }

    private Key getSiginKeys() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generates a key with at least 256 bits
    }

    // extractUsername
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) throws ExpiredJwtException, SignatureException {
        return Jwts.parserBuilder().setSigningKey(getSiginKeys()).build().parseClaimsJws(token).getBody();
    }

    // // validateToken
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // // isTokenExpired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }
}