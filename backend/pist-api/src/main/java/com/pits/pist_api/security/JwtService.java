package com.pits.pist_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessExpirationMs;
    private final long refreshExpirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration-ms}") long accessExpirationMs,
            @Value("${jwt.refresh-expiration-ms}") long refreshExpirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMs = accessExpirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateAccessToken(Long idCliente, String email) {
        return buildToken(idCliente, email, accessExpirationMs, "access");
    }

    public String generateRefreshToken(Long idCliente, String email) {
        return buildToken(idCliente, email, refreshExpirationMs, "refresh");
    }

    public long getAccessExpirationMs() {
        return accessExpirationMs;
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getClienteId(String token) {
        return parseToken(token).get("idCliente", Long.class);
    }

    public String getEmail(String token) {
        return parseToken(token).getSubject();
    }

    public String getTokenType(String token) {
        return parseToken(token).get("type", String.class);
    }

    private String buildToken(Long idCliente, String email, long expirationMs, String type) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .claim("idCliente", idCliente)
                .claim("type", type)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(signingKey)
                .compact();
    }
}
