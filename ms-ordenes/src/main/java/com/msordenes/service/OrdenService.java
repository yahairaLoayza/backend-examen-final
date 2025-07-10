package com.msordenes.service;

import com.msordenes.entity.Orden;

import java.util.List;

public interface OrdenService {
    Orden crear(Orden orden);
    List<Orden> listar();
    Orden actualizar(Long id, Orden orden);
    void eliminar(Long id);
}
