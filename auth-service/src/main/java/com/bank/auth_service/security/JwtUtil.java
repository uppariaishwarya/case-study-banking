package com.bank.auth_service.security;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // ✅ SAME SECRET MUST BE USED IN GATEWAY
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
   

    // ✅ GENERATE TOKEN (WITH ROLE)
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)                       // ✅ user
                .claim("role", "ADMIN")                        // ✅ role added
                .setIssuedAt(new Date())                    // ✅ creation time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // ✅ 1 hour
                .signWith(key)                              // ✅ sign
                .compact();
    }

    // ✅ VALIDATE TOKEN
    public Claims validateToken(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ EXTRACT USERNAME
    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    // ✅ EXTRACT ROLE
    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }
}
