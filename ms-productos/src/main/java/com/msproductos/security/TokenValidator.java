package com.msproductos.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class TokenValidator {

    @Value("${auth.validate-url}")
    private String validateUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void validateAdmin(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Token faltante o inválido");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    validateUrl, HttpMethod.GET, request, Map.class);

            String rol = (String) response.getBody().get("rol");

            if (!(rol.equals("ADMIN") || rol.equals("SUPERADMIN"))) {
                throw new RuntimeException("Acceso denegado: solo ADMIN o SUPERADMIN");
            }

        } catch (Exception e) {
            throw new RuntimeException("Token inválido: " + e.getMessage());
        }
    }
}
