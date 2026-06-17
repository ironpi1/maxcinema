package com.sala.salas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.salas.DTO.TipoAsientoDTO;
import com.sala.salas.model.TipoAsiento;
import com.sala.salas.repository.TipoAsientoRepository;

@Service
public class TipoAsientoService {

    @Autowired
    private TipoAsientoRepository tipoAsientoRepository;

    public List<TipoAsientoDTO> listar() {
        return tipoAsientoRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public TipoAsientoDTO buscarPorId(Integer id) {
        TipoAsiento tipoAsiento = tipoAsientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el tipo de asiento con ID: " + id));
        return convertirADTO(tipoAsiento);
    }

    public TipoAsientoDTO guardar(TipoAsientoDTO dto) {
        TipoAsiento tipoAsiento = new TipoAsiento();
        tipoAsiento.setTipo(dto.getTipo());
        return convertirADTO(tipoAsientoRepository.save(tipoAsiento));
    }

    public TipoAsientoDTO actualizar(Integer id, TipoAsientoDTO dto) {
        TipoAsiento tipoAsiento = tipoAsientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el tipo de asiento con ID: " + id));
        tipoAsiento.setTipo(dto.getTipo());
        return convertirADTO(tipoAsientoRepository.save(tipoAsiento));
    }

    public void eliminar(Integer id) {
        TipoAsiento tipoAsiento = tipoAsientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el tipo de asiento con ID: " + id));
        tipoAsientoRepository.delete(tipoAsiento);
    }

    private TipoAsientoDTO convertirADTO(TipoAsiento tipoAsiento) {
        TipoAsientoDTO dto = new TipoAsientoDTO();
        dto.setId(tipoAsiento.getId());
        dto.setTipo(tipoAsiento.getTipo());
        return dto;
    }
}