package org.company.algospectra_backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long jwtExpirationMs = 86400000; // 24 hrs

    // Generate token with additional claims
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setClaims(Map.of("role", role))
                .setSubject(email) // email as the subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey)
                .compact();
    }

    // Extract specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract email (subject)
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract role
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Check if token is valid
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Check if token belongs to a specific email
    public boolean isTokenValidForEmail(String token, String expectedEmail) {
        try {
            String tokenEmail = extractEmail(token);
            return expectedEmail.equals(tokenEmail) && validateToken(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Internal: extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
