package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.SalaDto;
import com.maxcinema.maxcinema.model.sala;
import com.maxcinema.maxcinema.repository.SalaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    private SalaDto convertirADTO(sala sala) {
    SalaDto dto = new SalaDto();
    dto.setId(sala.getId());
    dto.setNombre(sala.getNombre());
    dto.setNumeroDeSala(sala.getNumeroDeSala());

    if (sala.getTiposSalas() != null) {
        dto.setNombreTipoSala(sala.getTiposSalas().getNombre());
    }

    return dto;
    }

    public List<SalaDto> Listarsala() {
    return salaRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    public SalaDto guardarSala(sala sala) {
        sala guardado = salaRepository.save(sala);
        return convertirADTO(guardado);
    }

    public SalaDto actualizarSala(Integer id, sala Sala) {
        sala sala2 = salaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + " no existe"));
        if (Sala.getNombre() != null) {
        sala2.setNombre(Sala.getNombre());
        }
        if (Sala.getNumeroDeSala() != null) {
            sala2.setNumeroDeSala(Sala.getNumeroDeSala());
        }
        return convertirADTO(salaRepository.save(sala2));
    }

    public List<SalaDto> buscarSalaPorNombre(String nombre) {
        return salaRepository.findByNombre(nombre).stream()
            .map(this::convertirADTO)
            .toList();
    }

    public List<SalaDto> buscarSalaPorNumeroSala(Integer numeroSala) {
        return salaRepository.findByNumeroDeSala(numeroSala).stream()
            .map(this::convertirADTO)
            .toList();
    }
    public String eliminarSala(Integer id) {
    try {
        sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id, el id " + id + " no existe"));
        salaRepository.delete(sala);
        return "la sala '" + sala.getNombre() + "' fue eliminada exitosamente";
    } catch (RuntimeException e) {
        return e.getMessage();
    }
    }
    public SalaDto buscarSalaPorId(Integer id) {
        sala sala = salaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible encontrar la sala con id " + id));
        return convertirADTO(sala);
    }
}