package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${spring.jwt.secret}")
    private String key;

    @Value("${spring.jwt.expiration}")
    private Long expirationTime;


    public String generateToken (Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return buildToken(userDetails.getUsername());
    }

    public String buildToken (String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey () {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isTokenValid (String token) {
        try {
            extractClaims(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims (String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername (String token) {
        return extractClaims(token).getSubject();
    }

}
