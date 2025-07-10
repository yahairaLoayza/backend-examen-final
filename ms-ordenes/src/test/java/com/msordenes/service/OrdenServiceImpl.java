package com.msordenes.service;

import com.msordenes.entity.Orden;
import com.msordenes.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;

    @Override
    public Orden crear(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> listar() {
        return ordenRepository.findAll();
    }

    @Override
    public Orden actualizar(Long id, Orden orden) {
        Orden existente = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        existente.setDescripcion(orden.getDescripcion());
        existente.setTotal(orden.getTotal());
        existente.setFecha(orden.getFecha());

        return ordenRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!ordenRepository.existsById(id)) {
            throw new RuntimeException("Orden no encontrada");
        }
        ordenRepository.deleteById(id);
    }
}
