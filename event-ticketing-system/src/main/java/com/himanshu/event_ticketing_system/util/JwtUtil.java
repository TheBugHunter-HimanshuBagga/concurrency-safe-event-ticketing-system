package com.himanshu.event_ticketing_system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.securityKey}")
    private String jwtSecurityKey;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecurityKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email) // stores users identity , here - email
                .setIssuedAt(new Date()) // time of token which is issues at
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // valid unitl
                .signWith(key) //Signs token → prevents tampering
                .compact(); // header + payload + signature
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject(); // Reads email stored in token
    }

    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
/*
    what's the code doin

1. Generate tokens (access + refresh)
2. Read data from token (email)
3. Validate token (correct + not expired)

    jwt.securityKey=mysecretkeymysecretkeymysecretkey123

    String → byte[] → SecretKey
👉 Why?
JWT needs cryptographic key, not string
This key is used to sign tokens


🧩 Step 1: JWT Utility

✔️ You wrote methods:

generateAccessToken()
generateRefreshToken()
extractEmail()
isTokenValid()

👉 This is your token engine (no Spring Security here)
 */