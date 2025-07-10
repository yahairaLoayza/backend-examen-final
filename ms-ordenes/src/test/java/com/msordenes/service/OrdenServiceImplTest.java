package com.msordenes.service;

import com.msordenes.entity.Orden;
import com.msordenes.repository.OrdenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrdenServiceImplTest {

    @Mock
    private OrdenRepository ordenRepository;

    private OrdenService ordenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ordenService = new OrdenServiceImpl(ordenRepository);
    }

    @Test
    void testCrear() {
        Orden orden = new Orden(null, "Compra", 100.0, LocalDate.now());
        when(ordenRepository.save(any())).thenReturn(orden);

        Orden resultado = ordenService.crear(orden);

        assertEquals("Compra", resultado.getDescripcion());
    }

    @Test
    void testListar() {
        List<Orden> ordenes = List.of(new Orden(1L, "Compra", 100.0, LocalDate.now()));
        when(ordenRepository.findAll()).thenReturn(ordenes);

        List<Orden> resultado = ordenService.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void testActualizar() {
        Orden orden = new Orden(1L, "Compra", 100.0, LocalDate.now());
        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));
        when(ordenRepository.save(any())).thenReturn(orden);

        Orden actualizado = new Orden(null, "Compra actualizada", 150.0, LocalDate.now());
        Orden resultado = ordenService.actualizar(1L, actualizado);

        assertEquals("Compra actualizada", resultado.getDescripcion());
    }

    @Test
    void testEliminar() {
        when(ordenRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> ordenService.eliminar(1L));
        verify(ordenRepository, times(1)).deleteById(1L);
    }
}
