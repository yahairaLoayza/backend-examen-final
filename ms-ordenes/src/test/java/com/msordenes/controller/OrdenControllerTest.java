package com.msordenes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msordenes.config.SecurityByPassConfig;
import com.msordenes.entity.Orden;
import com.msordenes.security.TokenValidator;
import com.msordenes.service.OrdenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenController.class)
@Import(SecurityByPassConfig.class)
public class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;

    @MockBean
    private TokenValidator tokenValidator;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(tokenValidator).validateAdmin(Mockito.anyString());    }

    @Test
    void testCrear() throws Exception {
        Orden orden = new Orden(null, "Compra", 100.0, LocalDate.now());
        Mockito.when(ordenService.crear(any())).thenReturn(orden);

        mockMvc.perform(post("/ordenes")
                        .header("Authorization", "Bearer testtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk());
    }

    @Test
    void testListar() throws Exception {
        Mockito.when(ordenService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/ordenes")
                        .header("Authorization", "Bearer testtoken"))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizar() throws Exception {
        Orden orden = new Orden(1L, "Compra", 100.0, LocalDate.now());
        Mockito.when(ordenService.actualizar(Mockito.eq(1L), any())).thenReturn(orden);

        mockMvc.perform(put("/ordenes/1")
                        .header("Authorization", "Bearer testtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(ordenService).eliminar(1L);

        mockMvc.perform(delete("/ordenes/1")
                        .header("Authorization", "Bearer testtoken"))
                .andExpect(status().isNoContent());
    }
}
