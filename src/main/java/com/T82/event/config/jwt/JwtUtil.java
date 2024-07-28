package com.T82.event.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public TokenInfo parseToken(String token) {
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token)
                .getPayload();
        return TokenInfo.fromClaims(payload);
    }

    public JwtUtil(
            @Value("${jwt.secret}") String secret
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
}
