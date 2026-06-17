package com.sala.salas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.salas.DTO.TiposSalasDTO;
import com.sala.salas.model.TiposSalas;
import com.sala.salas.repository.TiposSalasRepository;

@Service
public class TiposSalasService {

    @Autowired
    private TiposSalasRepository tiposSalasRepository;

    public List<TiposSalasDTO> listar() {
        return tiposSalasRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public TiposSalasDTO buscarPorId(Integer id) {
        TiposSalas entidad = tiposSalasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el registro con ID: " + id));
        return convertirADTO(entidad);
    }

    public TiposSalasDTO guardar(TiposSalas entidad) {
        return convertirADTO(tiposSalasRepository.save(entidad));
    }

    public void eliminar(Integer id) {
        TiposSalas entidad = tiposSalasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el registro con ID: " + id));
        tiposSalasRepository.delete(entidad);
    }

    public TiposSalasDTO actualizar(Integer id, TiposSalas actualizado) {
        TiposSalas entidad = tiposSalasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el registro con ID: " + id));
        entidad.setNombre(actualizado.getNombre());
        entidad.setSala(actualizado.getSala());
        entidad.setTipoSala(actualizado.getTipoSala());
        
        return convertirADTO(tiposSalasRepository.save(entidad));
    }

    private TiposSalasDTO convertirADTO(TiposSalas entidad) {
        TiposSalasDTO dto = new TiposSalasDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());

        if (entidad.getSala() != null) {
            dto.setSalaId(entidad.getSala().getId());
            dto.setSalaNombre(entidad.getSala().getNombre());
        }
        if (entidad.getTipoSala() != null) {
            dto.setTipoSalaId(entidad.getTipoSala().getId());
            dto.setTipoSalaNombre(entidad.getTipoSala().getNombre());
        }
        return dto;
    }
}