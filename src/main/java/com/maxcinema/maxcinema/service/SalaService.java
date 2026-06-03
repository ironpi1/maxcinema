package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.SalaDto;
import com.maxcinema.maxcinema.model.Sala;
import com.maxcinema.maxcinema.model.TiposSalas;
import com.maxcinema.maxcinema.repository.SalaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    private SalaDto convertirADTO(Sala sala) {
    SalaDto dto = new SalaDto();
    dto.setId(sala.getId());
    dto.setNombre(sala.getNombre());
    dto.setNumeroDeSala(sala.getNumeroDeSala());

    if (sala.getTiposSalas() != null && !sala.getTiposSalas().isEmpty()) {
        TiposSalas relacion = sala.getTiposSalas().get(0);
        if (relacion.getTipoSala() != null) {
            dto.setNombreTipoSala(relacion.getTipoSala().getNombre());
        }
    }

    return dto;
    }

    public List<SalaDto> listarSala() {
    return salaRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    public SalaDto guardarSala(Sala sala) {
        Sala guardado = salaRepository.save(sala);
        return convertirADTO(guardado);
    }
    public SalaDto actualizarSala(Integer id, Sala sala) {
        Sala sala2 = salaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + " no existe"));
        if (sala.getNombre() != null) {
        sala2.setNombre(sala.getNombre());
        }
        if (sala.getNumeroDeSala() != null) {
            sala2.setNumeroDeSala(sala.getNumeroDeSala());
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
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id, el id " + id + " no existe"));
        salaRepository.delete(sala);
        return "la sala '" + sala.getNombre() + "' fue eliminada exitosamente";
    } catch (RuntimeException e) {
        return e.getMessage();
    }
    }
    public SalaDto buscarSalaPorId(Integer id) {
        Sala sala = salaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible encontrar la sala con id " + id));
        return convertirADTO(sala);
    }
}