package com.maxcinema.maxcinema.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.dto.SalasPeliculaDTO;
import com.maxcinema.maxcinema.model.SalasPelicula;
import com.maxcinema.maxcinema.repository.SalasPeliculaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalasPeliculaService {

    @Autowired
    private SalasPeliculaRepository salasPeliculaRepository;

    public List<SalasPeliculaDTO> obtenerTodos() {
        List<SalasPelicula> lista = salasPeliculaRepository.findAll();
        log.info("Se encontraron {} registros en SalasPelicula", lista.size());
        return lista.stream().map(this::convertirADTO).toList();
    }

    public SalasPeliculaDTO buscarPorId(Integer id) {
        log.debug("Buscando salas de película con ID {}", id);
        SalasPelicula salasPelicula = salasPeliculaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontraron salas de película con ID {}", id);
                    return new RuntimeException("No existen salas de película con ID: " + id);
                });
        log.info("Salas de película encontradas con ID {}", id);
        return convertirADTO(salasPelicula);
    }

    public SalasPeliculaDTO guardar(SalasPelicula salasPelicula) {
        log.debug("Guardando: Película={}, Sala={}",
                  salasPelicula.getPelicula().getTitulo(), salasPelicula.getSala().getNombre());
        SalasPelicula guardado = salasPeliculaRepository.save(salasPelicula);
        log.info("Salas de película guardadas con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public SalasPeliculaDTO actualizar(Integer id, SalasPelicula actualizado) {
        log.debug("Actualizando SalasPelicula con ID {}", id);
        SalasPelicula salasPelicula = salasPeliculaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede actualizar. Salas de películacon ID {} no existen", id);
                    return new RuntimeException("No se puede actualizar. No existen Salas de película con ID " + id);
                });

        salasPelicula.setPelicula(actualizado.getPelicula());
        salasPelicula.setSala(actualizado.getSala());

        SalasPelicula guardado = salasPeliculaRepository.save(salasPelicula);
        log.info("Salas de película actualizadas correctamente con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public String eliminar(Integer id) {
        log.debug("Eliminando Salas de película con ID {}", id);
        SalasPelicula salasPelicula = salasPeliculaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede eliminar. Salas de película con ID {} no existen", id);
                    return new RuntimeException("No se puede eliminar. No existen Salas de película con ID " + id);
                });
        salasPeliculaRepository.delete(salasPelicula);
        log.info("Salas de película eliminadas con ID {}", id);
        return "Salas de película con ID " + id + " eliminadas exitosamente.";
    }
    
    //Salas asociadas a una película
    public List<SalasPeliculaDTO> buscarPorPelicula(Integer peliculaId) {
        log.debug("Buscando salas asociadas a la película con ID {}", peliculaId);
        List<SalasPelicula> lista = salasPeliculaRepository.findByPeliculaId(peliculaId);
        if (lista.isEmpty()) {
            log.warn("No se encontraron salas para la película con ID {}", peliculaId);
            throw new RuntimeException("No existen salas para la película con ID: " + peliculaId);
        }
        log.info("Se encontraron {} salas para la película con ID {}", lista.size(), peliculaId);
        return lista.stream().map(this::convertirADTO).toList();
    }

    //Películas asociadas a una sala
    public List<SalasPeliculaDTO> buscarPorSala(Integer salaId) {
        log.debug("Buscando películas asociadas a la sala con ID {}", salaId);
        List<SalasPelicula> lista = salasPeliculaRepository.findBySalaId(salaId);
        if (lista.isEmpty()) {
            log.warn("No se encontraron películas para la sala con ID {}", salaId);
            throw new RuntimeException("No existen películas para la sala con ID: " + salaId);
        }
        log.info("Se encontraron {} películas para la sala con ID {}", lista.size(), salaId);
        return lista.stream().map(this::convertirADTO).toList();
    }

    private SalasPeliculaDTO convertirADTO(SalasPelicula salasPelicula) {
        SalasPeliculaDTO dto = new SalasPeliculaDTO();
        dto.setId(salasPelicula.getId());
        dto.setPeliculaId(salasPelicula.getPelicula().getId());
        dto.setPeliculaTitulo(salasPelicula.getPelicula().getTitulo());
        dto.setSalaId(salasPelicula.getSala().getId());
        dto.setSalaNombre(salasPelicula.getSala().getNombre());
        return dto;
    }
}
