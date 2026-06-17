package com.sala.salas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.salas.DTO.AsientoDTO;
import com.sala.salas.model.Asiento;
import com.sala.salas.repository.AsientoRepository;

@Service
public class AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;

    public List<AsientoDTO> listar() {
        return asientoRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public AsientoDTO buscarPorId(Integer id) {
        Asiento asiento = asientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el asiento con ID: " + id));
        return convertirADTO(asiento);
    }

    public AsientoDTO guardar(Asiento asiento) {
        return convertirADTO(asientoRepository.save(asiento));
    }

    public void eliminar(Integer id) {
        Asiento asiento = asientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el asiento con ID: " + id));
        asientoRepository.delete(asiento);
    }

    public AsientoDTO actualizar(Integer id, Asiento actualizado) {
        Asiento asiento = asientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el asiento con ID: " + id));
        asiento.setFila(actualizado.getFila());
        asiento.setColumna(actualizado.getColumna());
        asiento.setEstado(actualizado.getEstado());
        asiento.setTipoAsiento(actualizado.getTipoAsiento());

        return convertirADTO(asientoRepository.save(asiento));
    }
    
    private AsientoDTO convertirADTO(Asiento asiento) {
        AsientoDTO dto = new AsientoDTO();
        dto.setId(asiento.getId());
        dto.setFila(asiento.getFila());
        dto.setColumna(asiento.getColumna());
        dto.setEstado(asiento.getEstado());

        if (asiento.getTipoAsiento() != null) {
            dto.setTipoAsientoId(asiento.getTipoAsiento().getId());
            dto.setTipoAsientoNombre(asiento.getTipoAsiento().getTipo());
        }
        return dto;
    }
}