package com.msauth.service;

import com.msauth.dto.AuthResponse;
import com.msauth.dto.LoginRequest;
import com.msauth.dto.RegisterRequest;
import com.msauth.entity.Rol;
import com.msauth.entity.Usuario;
import com.msauth.repository.UsuarioRepository;
import com.msauth.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("123");
        request.setRol(Rol.USER);

        Usuario savedUser = Usuario.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .password("encoded123")
                .rol(Rol.USER)
                .build();

        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(Usuario.class))).thenReturn("mocked-jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
    }

    @Test
    void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123");

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .password("encoded123")
                .rol(Rol.USER)
                .build();

        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("123", "encoded123")).thenReturn(true);
        when(jwtService.generateToken(usuario)).thenReturn("mocked-jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
    }

    @Test
    void testValidateTokenSuccess() {
        String token = "mocked-token";
        String email = "test@example.com";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email(email)
                .rol(Rol.USER)
                .build();

        when(jwtService.extractUsername(token)).thenReturn(email);
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Usuario result = authService.validate(token);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }
}
