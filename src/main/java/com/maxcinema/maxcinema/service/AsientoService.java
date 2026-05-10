package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.AsientoDTO;
import com.maxcinema.maxcinema.model.Asiento;
import com.maxcinema.maxcinema.repository.AsientoRepository;

@Service
public class AsientoService {

    @Autowired
    AsientoRepository asientoRepository;
    
    public List<AsientoDTO> obtenerTodos() {
        return asientoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public String verificarDisponibilidad(Boolean estado) {
        if (estado) {
            return "El asiento está disponible";
        } else {
            return "El asiento no está disponible";
        }
    }

    public List<AsientoDTO> obtenerAsientosDisponibles() {
        return asientoRepository.findByEstado(true).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public AsientoDTO convertirADTO(Asiento asiento) {
        AsientoDTO dto = new AsientoDTO();
        dto.setFila(asiento.getFila());
        dto.setColumna(asiento.getColumna());
        dto.setEstado(asiento.getEstado());
        return dto;
    }
    
}
