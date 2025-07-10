package com.msauth.service;

import com.msauth.dto.AuthResponse;
import com.msauth.dto.LoginRequest;
import com.msauth.dto.RegisterRequest;
import com.msauth.entity.Usuario;
import com.msauth.repository.UsuarioRepository;
import com.msauth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .build();
        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token);
    }

    public Usuario validate(String token) {
        String email = jwtService.extractUsername(token);
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Token inválido"));
    }

    // 🔹 Nuevo metodo: obtener el email desde el token
    public String getEmail(String token) {
        return jwtService.extractUsername(token);
    }
}
