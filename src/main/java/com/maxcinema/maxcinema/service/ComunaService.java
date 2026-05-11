package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.ComunaDto;
import com.maxcinema.maxcinema.model.comuna;
import com.maxcinema.maxcinema.repository.ComunaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    private ComunaDto convertirADTO(comuna comuna) {
        ComunaDto dto = new ComunaDto();
        dto.setComuna_id(comuna.getId());
        dto.setNombre(comuna.getNombre());
        return dto;
    }

    public List<ComunaDto> ListarComuna() {
        return comunaRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    public ComunaDto guardarComuna(comuna comuna) {
        comuna guardado = comunaRepository.save(comuna);
        return convertirADTO(guardado);
    }

    public ComunaDto actualizarcComuna(Integer id, comuna Comuna) {
        comuna comuna2 = comunaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + " no existe"));
        if (Comuna.getNombre() != null) {
        comuna2.setNombre(Comuna.getNombre());
    }
        return convertirADTO(comunaRepository.save(comuna2));
    }

    public List<ComunaDto> buscarComunaPorNombre(String nombre) {
        return comunaRepository.findByNombre(nombre).stream()
            .map(this::convertirADTO)
         .toList();
    }
    public String eliminarComuna(Integer id) {
    try {
        comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id " + id + " no existe"));
        comunaRepository.delete(comuna);
        return "la comuna '" + comuna.getNombre() + "' fue eliminada exitosamente";
    } catch (RuntimeException e) {
        return e.getMessage();
    }
    }
    public ComunaDto buscarComunaPorId(Integer id) {
        comuna comuna = comunaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible encontrar la comuna con id " + id));
        return convertirADTO(comuna);
    }
}