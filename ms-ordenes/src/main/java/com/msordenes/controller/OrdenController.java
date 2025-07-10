package com.msordenes.controller;

import com.msordenes.entity.Orden;
import com.msordenes.security.TokenValidator;
import com.msordenes.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;
    private final TokenValidator tokenValidator;

    @PostMapping
    public ResponseEntity<Orden> crear(@RequestHeader("Authorization") String token,
                                       @RequestBody Orden orden) {
        tokenValidator.validateAdmin(token);
        return ResponseEntity.ok(ordenService.crear(orden));
    }

    @GetMapping
    public ResponseEntity<List<Orden>> listar(@RequestHeader("Authorization") String token) {
        tokenValidator.validateAdmin(token);
        return ResponseEntity.ok(ordenService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orden> actualizar(@RequestHeader("Authorization") String token,
                                            @PathVariable Long id,
                                            @RequestBody Orden orden) {
        tokenValidator.validateAdmin(token);
        return ResponseEntity.ok(ordenService.actualizar(id, orden));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@RequestHeader("Authorization") String token,
                                         @PathVariable Long id) {
        tokenValidator.validateAdmin(token);
        ordenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
