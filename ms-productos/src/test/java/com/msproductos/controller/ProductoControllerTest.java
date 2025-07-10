package com.msproductos.controller;

import com.msproductos.entity.Producto;
import com.msproductos.security.TokenValidator;
import com.msproductos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private TokenValidator tokenValidator;

    @InjectMocks
    private ProductoController productoController;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        producto = Producto.builder()
                .id(1L)
                .nombre("Teclado")
                .precio(120.0)
                .categoria("Accesorios")
                .build();
    }

    @Test
    void testCrearProducto() {
        when(productoService.crear(producto)).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.crear("Bearer token", producto);

        verify(tokenValidator).validateAdmin("Bearer token");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(producto, response.getBody());
    }

    @Test
    void testListarProductos() {
        List<Producto> productos = List.of(producto);
        when(productoService.listar()).thenReturn(productos);

        ResponseEntity<List<Producto>> response = productoController.listar("Bearer token");

        verify(tokenValidator).validateAdmin("Bearer token");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productos, response.getBody());
    }

    @Test
    void testActualizarProducto() {
        Producto actualizado = Producto.builder()
                .id(1L)
                .nombre("Teclado mecánico")
                .precio(200.0)
                .categoria("Accesorios")
                .build();

        when(productoService.actualizar(1L, actualizado)).thenReturn(actualizado);

        ResponseEntity<Producto> response = productoController.actualizar("Bearer token", 1L, actualizado);

        verify(tokenValidator).validateAdmin("Bearer token");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Teclado mecánico", response.getBody().getNombre());
    }

    @Test
    void testEliminarProducto() {
        doNothing().when(productoService).eliminar(1L);

        ResponseEntity<Void> response = productoController.eliminar("Bearer token", 1L);

        verify(tokenValidator).validateAdmin("Bearer token");
        verify(productoService).eliminar(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
