package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.dto.GeneroDTO;
import com.maxcinema.maxcinema.model.Genero;
import com.maxcinema.maxcinema.repository.GeneroRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<GeneroDTO> obtenerTodos() {
        return generoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }
    
    public GeneroDTO buscarPorNombre(String nombre) {
        Genero genero = generoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("No existe ese género"));
        return convertirADTO(genero);
    }

    public GeneroDTO guardar(Genero genero) {
        Genero guardado = generoRepository.save(genero);
        log.info("Genero guardado con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public GeneroDTO buscarPorId(Integer id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe género con ID: " + id));
        return convertirADTO(genero);
    }

    public GeneroDTO actualizar(Integer id, Genero actualizado) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar. No existe género con ID " + id));
        genero.setNombre(actualizado.getNombre());

        Genero guardado = generoRepository.save(genero);
        log.info("Genero actualizado con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public String eliminar(Integer id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar. No existe género con ID " + id));
        generoRepository.delete(genero);
        log.info("Genero eliminado con ID {}", id);
        return "Genero con ID " + id + " ha sido eliminado exitosamente.";
    }

    private GeneroDTO convertirADTO(Genero genero) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(genero.getId());
        dto.setNombre(genero.getNombre());
        return dto;
    }
}

