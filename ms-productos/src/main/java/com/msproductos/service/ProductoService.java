package com.msproductos.service;

import com.msproductos.entity.Producto;
import java.util.List;

public interface ProductoService {
    Producto crear(Producto producto);
    List<Producto> listar();
    Producto actualizar(Long id, Producto producto);
    void eliminar(Long id);
}
