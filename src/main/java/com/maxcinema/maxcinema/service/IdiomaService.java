package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.dto.IdiomaDTO;
import com.maxcinema.maxcinema.model.Idioma;
import com.maxcinema.maxcinema.repository.IdiomaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IdiomaService {

    @Autowired
    private IdiomaRepository idiomaRepository;

    public List<IdiomaDTO> obtenerTodos() {
        log.debug("Obteniendo todos los idiomas");
        return idiomaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public IdiomaDTO buscarPorId(Integer id) {
        log.debug("Buscando idioma con ID {}", id);
        Idioma idioma = idiomaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Idioma no encontrado con ID {}", id);
                    return new RuntimeException("No existe idioma con ID: " + id);
                });
        log.info("Idioma encontrado con ID {}", id);
        return convertirADTO(idioma);
    }

    public IdiomaDTO buscarPorNombre(String nombre) {
        log.debug("Buscando idioma exacto con nombre: {}", nombre);
        Idioma idioma = idiomaRepository.findByNombre(nombre)
                .orElseThrow(() -> {
                    log.warn("No existe idioma con nombre: {}", nombre);
                    return new RuntimeException("No existe ese idioma");
                });
        log.info("Idioma encontrado: {}", idioma.getNombre());
        return convertirADTO(idioma);
    }

    public IdiomaDTO guardar(Idioma idioma) {
        Idioma guardado = idiomaRepository.save(idioma);
        return convertirADTO(guardado);
    }

    public IdiomaDTO actualizar(Integer id, Idioma actualizado) {
        log.info("Actualizando idioma con ID {}", id);
        Idioma idioma = idiomaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede actualizar. Idioma con ID {} no existe", id);
                    return new RuntimeException("No se puede actualizar. No existe idioma con ID " + id);
                });

        idioma.setNombre(actualizado.getNombre());
        Idioma guardado = idiomaRepository.save(idioma);
        log.info("Idioma actualizado correctamente con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public String eliminar(Integer id) {
        log.info("Eliminando idioma con ID {}", id);
        Idioma idioma = idiomaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede eliminar. Idioma con ID {} no existe", id);
                    return new RuntimeException("No se puede eliminar. No existe idioma con ID " + id);
                });
        idiomaRepository.delete(idioma);
        log.info("Idioma eliminado con ID {}", id);
        return "Idioma con ID " + id + " ha sido eliminado exitosamente.";
    }
    
    private IdiomaDTO convertirADTO(Idioma idioma) {
        IdiomaDTO dto = new IdiomaDTO();
        dto.setId(idioma.getId());
        dto.setNombre(idioma.getNombre());
        return dto;
}
}
