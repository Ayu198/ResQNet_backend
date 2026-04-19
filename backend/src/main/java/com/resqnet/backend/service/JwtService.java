package com.resqnet.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(java.util.
                Base64.getEncoder().encodeToString(secretKey.getBytes()));
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Long userId , String phoneNumber) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber",phoneNumber);
        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(userId))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(signingKey)
                .compact();
    }

    public String extractUserid(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractPhoneNumber(String token) {
        return extractAllClaims(token).get("phoneNumber" , String.class);
    }

    public boolean isTokenValid(String token , Long userId) {
        String extractUserId = extractUserid(token);
        return extractUserId.equals(String.valueOf(userId)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }
}
