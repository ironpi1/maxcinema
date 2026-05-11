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

    public AsientoDTO buscarPorId(Integer id) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));
        return convertirADTO(asiento);
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

    public Asiento editarAsiento(Integer id, Asiento asiento) {
        Asiento asient = asientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Asiento no encontrado"));
        if (asiento.getEstado() != null) {
            asient.setEstado(asiento.getEstado());
        }
        return asientoRepository.save(asient);
    }

    public Asiento guardarAsiento(Asiento asiento) {
        return asientoRepository.save(asiento);
    }

    public AsientoDTO actualizarAsiento(Integer id, AsientoDTO dto) {
        Asiento asiento = asientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Asiento no encontrado"));
        if (dto.getFila() != null) asiento.setFila(dto.getFila());
        if (dto.getColumna() != null) asiento.setColumna(dto.getColumna());
        if (dto.getEstado() != null) asiento.setEstado(dto.getEstado());
        Asiento saved = asientoRepository.save(asiento);
        return convertirADTO(saved);
    }

    public String eliminarAsiento(Integer id) {
        try {
            Asiento asiento = asientoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! El asiento con ID " + id + " no existe."));
            asientoRepository.delete(asiento);
            return "El asiento '" + asiento.getFila() + "-" + asiento.getColumna() + "' ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

        public AsientoDTO convertirADTO(Asiento asiento) {
        AsientoDTO dto = new AsientoDTO();
        dto.setId(asiento.getId());
        dto.setFila(asiento.getFila());
        dto.setColumna(asiento.getColumna());
        dto.setEstado(asiento.getEstado());
        dto.setTipoAsiento(asiento.getTipoAsiento() != null ? asiento.getTipoAsiento().getId() : null);
        return dto;
    }
    
}
