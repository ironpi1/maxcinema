package com.pelicula.peliculas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pelicula.peliculas.DTO.GenerosDTO;
import com.pelicula.peliculas.model.Generos;
import com.pelicula.peliculas.repository.GenerosRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenerosService {

    private GenerosRepository generosRepository;

    public List<GenerosDTO> obtenerTodos() {
        return generosRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }
    
    public GenerosDTO buscarPorId(Integer id) {
        log.debug("Buscando géneros con ID {}", id);
        Generos generos = generosRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontraron géneros con ID {}", id);
                    return new RuntimeException("No existen géneros con ID: " + id);
                });
        log.info("Géneros encontrados con ID {}", id);
        return convertirADTO(generos);
    }

    public GenerosDTO guardar(Generos generos) {
        log.debug("Intentando guardar géneros: Película={}, Género={}",
                  generos.getPelicula().getTitulo(), generos.getGenero().getNombre());
        Generos guardado = generosRepository.save(generos);
        log.info("Géneros guardados exitosamente con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public GenerosDTO actualizar(Integer id, Generos actualizado) {
        log.debug("Intentando actualizar géneros con ID {}", id);
        Generos generos = generosRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede actualizar. Géneros con ID {} no existe", id);
                    return new RuntimeException("No se puede actualizar. No existen géneros con ID " + id);
                });

        generos.setPelicula(actualizado.getPelicula());
        generos.setGenero(actualizado.getGenero());

        Generos guardado = generosRepository.save(generos);
        log.info("Géneros actualizados correctamente con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public String eliminar(Integer id) {
        log.debug("Intentando eliminar géneros con ID {}", id);
        Generos generos = generosRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede eliminar. Géneros con ID {} no existe", id);
                    return new RuntimeException("No se puede eliminar. No existen géneros con ID " + id);
                });
        generosRepository.delete(generos);
        log.info("Géneros eliminados exitosamente con ID {}", id);
        return "Géneros con ID " + id + " han sido eliminados exitosamente.";
    }

    //Géneros asociados a una película
    public List<GenerosDTO> buscarPorPelicula(Integer peliculaId) {
        log.debug("Buscando géneros asociados a la película con ID {}", peliculaId);
        List<Generos> lista = generosRepository.findByPeliculaId(peliculaId);
        if (lista.isEmpty()) {
            log.warn("No se encontraron géneros para la película con ID {}", peliculaId);
            throw new RuntimeException("No existen géneros para la película con ID: " + peliculaId);
        }
        log.info("Se encontraron {} géneros para la película con ID {}", lista.size(), peliculaId);
        return lista.stream().map(this::convertirADTO).toList();
    }

    //Películas asociadas a un género
    public List<GenerosDTO> buscarPorGenero(Integer generoId) {
        log.debug("Buscando películas asociadas al género con ID {}", generoId);
        List<Generos> lista = generosRepository.findByGeneroId(generoId);
        if (lista.isEmpty()) {
            log.warn("No se encontraron películas para el género con ID {}", generoId);
            throw new RuntimeException("No existen películas para el género con ID: " + generoId);
        }
        log.info("Se encontraron {} películas para el género con ID {}", lista.size(), generoId);
        return lista.stream().map(this::convertirADTO).toList();
    }

    private GenerosDTO convertirADTO(Generos generos) {
        GenerosDTO dto = new GenerosDTO();
        dto.setId(generos.getId());
        dto.setPeliculaId(generos.getPelicula().getId());
        dto.setPeliculaTitulo(generos.getPelicula().getTitulo());
        dto.setGeneroId(generos.getGenero().getId());
        dto.setGeneroNombre(generos.getGenero().getNombre());
        return dto;
    }
}
