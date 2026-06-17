package com.sala.salas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.salas.DTO.SalaDTO;
import com.sala.salas.model.Sala;
import com.sala.salas.repository.SalaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public List<SalaDTO> listarSalas() {
        return salaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public SalaDTO buscarPorId(Integer id) {
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró la sala con ID: " + id));
        return convertirADTO(sala);
    }

    public SalaDTO guardarSala(SalaDTO dto) {
        Sala sala = new Sala();
        sala.setNombre(dto.getNombre());
        sala.setNumeroDeSala(dto.getNumeroDeSala());
        return convertirADTO(salaRepository.save(sala));
    }

    public SalaDTO actualizarSala(Integer id, SalaDTO dto) {
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró la sala con ID: " + id));
        sala.setNombre(dto.getNombre());
        sala.setNumeroDeSala(dto.getNumeroDeSala());
        return convertirADTO(salaRepository.save(sala));
    }

    public void eliminarSala(Integer id) {
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró la sala con ID: " + id));
        salaRepository.delete(sala);
    }

    private SalaDTO convertirADTO(Sala sala) {
        SalaDTO dto = new SalaDTO();
        dto.setId(sala.getId());
        dto.setNombre(sala.getNombre());
        dto.setNumeroDeSala(sala.getNumeroDeSala());
        return dto;
    }
}