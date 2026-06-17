package com.sala.salas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.salas.DTO.TipoSalaDTO;
import com.sala.salas.model.TipoSala;
import com.sala.salas.repository.TipoSalaRepository;

@Service
public class TipoSalaService {

    @Autowired
    private TipoSalaRepository tipoSalaRepository;

    public List<TipoSalaDTO> listar() {
        return tipoSalaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public TipoSalaDTO buscarPorId(Integer id) {
        TipoSala tipoSala = tipoSalaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el tipo de sala con ID: " + id));
        return convertirADTO(tipoSala);
    }

    public TipoSalaDTO guardar(TipoSalaDTO dto) {
        TipoSala tipoSala = new TipoSala();
        tipoSala.setNombre(dto.getNombre());
        return convertirADTO(tipoSalaRepository.save(tipoSala));
    }

    public TipoSalaDTO actualizar(Integer id, TipoSalaDTO dto) {
        TipoSala tipoSala = tipoSalaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el tipo de sala con ID: " + id));
        tipoSala.setNombre(dto.getNombre());
        return convertirADTO(tipoSalaRepository.save(tipoSala));
    }

    public void eliminar(Integer id) {
        TipoSala tipoSala = tipoSalaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el tipo de sala con ID: " + id));
        tipoSalaRepository.delete(tipoSala);
    }

    private TipoSalaDTO convertirADTO(TipoSala tipoSala) {
        TipoSalaDTO dto = new TipoSalaDTO();
        dto.setId(tipoSala.getId());
        dto.setNombre(tipoSala.getNombre());
        return dto;
    }
}
