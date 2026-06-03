package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.TipoAsientoDTO;
import com.maxcinema.maxcinema.model.TipoAsiento;
import com.maxcinema.maxcinema.repository.TipoAsientoRepository;

@Service
public class TipoAsientoService {
    @Autowired
    private TipoAsientoRepository tipoAsientoRepository;

    public List<TipoAsientoDTO> obtenerTodos() {
        return tipoAsientoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoAsientoDTO buscarPorId(Integer id) {
        TipoAsiento tipoAsiento = tipoAsientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de asiento no encontrado"));
        return convertirADTO(tipoAsiento);
    }
    
    public  TipoAsiento crearTipoAsiento(TipoAsiento tipoAsiento) {
        return tipoAsientoRepository.save(tipoAsiento);
    }

    public TipoAsiento editarTipoAsiento(Integer id, TipoAsiento tipoAsiento) {
        TipoAsiento tipo = tipoAsientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Asiento no encontrado"));
        if (tipo.getTipo() != null) {
            tipo.setTipo(tipoAsiento.getTipo());
        }
        return tipoAsientoRepository.save(tipo);
    }

    public TipoAsientoDTO convertirADTO(TipoAsiento tipoAsiento) {
        TipoAsientoDTO dto = new TipoAsientoDTO();
        dto.setId(tipoAsiento.getId());
        dto.setTipo(tipoAsiento.getTipo());
        return dto;
    }
}
