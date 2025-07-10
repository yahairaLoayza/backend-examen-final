package com.msordenes.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {

    private final String secretKey = "m9T2x!qLz$g#X8dR5V@h1cZj*K7fNbAe";

    public boolean validateToken(String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(jwt);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public void validateAdmin(String token) {
        String role = extractRole(token);
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Acceso denegado: solo rol ADMIN");
        }
    }

    private String extractRole(String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims.get("role", String.class);
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido", e);
        }
    }
}
