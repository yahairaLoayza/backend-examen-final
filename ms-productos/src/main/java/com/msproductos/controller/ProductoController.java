package com.msproductos.controller;

import com.msproductos.entity.Producto;
import com.msproductos.security.TokenValidator;
import com.msproductos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final TokenValidator tokenValidator;

    @PostMapping
    public ResponseEntity<Producto> crear(
            @RequestHeader("Authorization") String token,
            @RequestBody Producto producto) {

        tokenValidator.validateAdmin(token);
        return ResponseEntity.ok(productoService.crear(producto));
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(
            @RequestHeader("Authorization") String token) {

        tokenValidator.validateAdmin(token);
        return ResponseEntity.ok(productoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Producto producto) {

        tokenValidator.validateAdmin(token);
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {

        tokenValidator.validateAdmin(token);
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
