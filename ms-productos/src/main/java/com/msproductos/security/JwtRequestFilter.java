package com.msproductos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            RestTemplate restTemplate = new RestTemplate();
            try {
                String validateUrl = "http://localhost:9000/auth/validate";
                var headers = new org.springframework.http.HttpHeaders();
                headers.set("Authorization", "Bearer " + jwt);
                var entity = new org.springframework.http.HttpEntity<>(headers);

                var responseEntity = restTemplate.exchange(
                        validateUrl,
                        org.springframework.http.HttpMethod.GET,
                        entity,
                        String.class
                );


            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Falta el token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
