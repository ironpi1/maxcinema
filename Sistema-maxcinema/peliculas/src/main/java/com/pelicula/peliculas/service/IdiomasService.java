package com.pelicula.peliculas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelicula.peliculas.DTO.IdiomasDTO;
import com.pelicula.peliculas.model.Idiomas;
import com.pelicula.peliculas.repository.IdiomasRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IdiomasService {

    @Autowired

    private IdiomasRepository idiomasRepository;

    public List<IdiomasDTO> obtenerTodos() {
        List<Idiomas> lista = idiomasRepository.findAll();
        log.info("Se encontraron {} registros en Idiomas", lista.size());
        return lista.stream().map(this::convertirADTO).toList();
    }

    public IdiomasDTO buscarPorId(Integer id) {
        log.debug("Buscando idiomas con ID {}", id);
        Idiomas idiomas = idiomasRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontraron idiomas con ID {}", id);
                    return new RuntimeException("No existen idiomas con ID: " + id);
                });
        log.info("Idiomas encontrados con ID {}", id);
        return convertirADTO(idiomas);
    }

    public IdiomasDTO guardar(Idiomas idiomas) {
        log.debug("Guardando idiomas: Película={}, Idioma={}",
                  idiomas.getPelicula().getTitulo(), idiomas.getIdioma().getNombre());
        Idiomas guardado = idiomasRepository.save(idiomas);
        log.info("Idiomas guardados con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public IdiomasDTO actualizar(Integer id, Idiomas actualizado) {
        log.debug("Actualizando idiomas con ID {}", id);
        Idiomas idiomas = idiomasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede actualizar. Idiomas con ID {} no existen", id);
                    return new RuntimeException("No se puede actualizar. No existen idiomas con ID " + id);
                });

        idiomas.setPelicula(actualizado.getPelicula());
        idiomas.setIdioma(actualizado.getIdioma());

        Idiomas guardado = idiomasRepository.save(idiomas);
        log.info("Idiomas actualizados correctamente con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    //Idiomas asociados a una película
    public List<IdiomasDTO> buscarPorPelicula(Integer peliculaId) {
        log.debug("Buscando idiomas asociados a la película con ID {}", peliculaId);
        List<Idiomas> lista = idiomasRepository.findByPeliculaId(peliculaId);
        if (lista.isEmpty()) {
            log.warn("No se encontraron idiomas para la película con ID {}", peliculaId);
            throw new RuntimeException("No existen idiomas para la película con ID: " + peliculaId);
        }
        log.info("Se encontraron {} idiomas para la película con ID {}", lista.size(), peliculaId);
        return lista.stream().map(this::convertirADTO).toList();
    }

    //Películas asociadas a un idioma
    public List<IdiomasDTO> buscarPorIdioma(Integer idiomaId) {
        log.debug("Buscando películas asociadas al idioma con ID {}", idiomaId);
        List<Idiomas> lista = idiomasRepository.findByIdiomaId(idiomaId);
        if (lista.isEmpty()) {
            log.warn("No se encontraron películas para el idioma con ID {}", idiomaId);
            throw new RuntimeException("No existen películas para el idioma con ID: " + idiomaId);
        }
        log.info("Se encontraron {} películas para el idioma con ID {}", lista.size(), idiomaId);
        return lista.stream().map(this::convertirADTO).toList();
    }

    public String eliminar(Integer id) {
        log.debug("Eliminando Idiomas con ID {}", id);
        Idiomas idiomas = idiomasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede eliminar. Idiomas con ID {} no existen", id);
                    return new RuntimeException("No se puede eliminar. No existen Idiomas con ID " + id);
                });
        idiomasRepository.delete(idiomas);
        log.info("Idiomas eliminados con ID {}", id);
        return "Idiomas con ID " + id + " eliminados exitosamente.";
    }

    private IdiomasDTO convertirADTO(Idiomas idiomas) {
        log.debug("Convirtiendo Idiomas con ID {} a DTO", idiomas.getId());
        IdiomasDTO dto = new IdiomasDTO();
        dto.setId(idiomas.getId());
        dto.setPeliculaId(idiomas.getPelicula().getId());
        dto.setPeliculaTitulo(idiomas.getPelicula().getTitulo());
        dto.setIdiomaId(idiomas.getIdioma().getId());
        dto.setIdiomaNombre(idiomas.getIdioma().getNombre());
        log.trace("DTO generado: {}", dto);
        return dto;
    }
}
