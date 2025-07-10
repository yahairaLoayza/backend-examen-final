package com.msproductos.service;

import com.msproductos.entity.Producto;
import com.msproductos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = Producto.builder()
                .id(1L)
                .nombre("Laptop")
                .precio(2500.0)
                .categoria("Tecnología")
                .build();
    }

    @Test
    void testCrearProducto() {
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto creado = productoService.crear(producto);
        assertNotNull(creado);
        assertEquals("Laptop", creado.getNombre());
    }

    @Test
    void testListarProductos() {
        List<Producto> productos = List.of(producto);
        when(productoRepository.findAll()).thenReturn(productos);
        List<Producto> resultado = productoService.listar();
        assertEquals(1, resultado.size());
    }

    @Test
    void testActualizarProductoExistente() {
        Producto actualizado = Producto.builder()
                .nombre("Laptop Gamer")
                .precio(3000.0)
                .categoria("Electrónica")
                .build();

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto resultado = productoService.actualizar(1L, actualizado);
        assertEquals("Laptop Gamer", resultado.getNombre());
        assertEquals(3000.0, resultado.getPrecio());
    }

    @Test
    void testActualizarProductoNoExistente() {
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productoService.actualizar(2L, producto));
    }

    @Test
    void testEliminarProductoExistente() {
        when(productoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productoRepository).deleteById(1L);
        assertDoesNotThrow(() -> productoService.eliminar(1L));
    }

    @Test
    void testEliminarProductoNoExistente() {
        when(productoRepository.existsById(2L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> productoService.eliminar(2L));
    }
}
