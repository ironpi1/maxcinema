package com.pelicula.peliculas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelicula.peliculas.DTO.PeliculaDTO;
import com.pelicula.peliculas.model.Pelicula;
import com.pelicula.peliculas.repository.PeliculaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<PeliculaDTO> obtenerTodas() {
        log.info("Obteniendo todas las películas");
        List<PeliculaDTO> peliculas = new ArrayList<>();
        for (Pelicula pelicula : peliculaRepository.findAll()) {
            peliculas.add(convertirADTO(pelicula));
        }
        log.info("Se encontraron {} películas", peliculas.size());
        return peliculas;
    }

    public PeliculaDTO buscarPorId(Integer id) {
        log.info("Buscando película con ID: {}", id);
        Pelicula pelicula = peliculaRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No se encontró película con ID: {}", id);
                return new RuntimeException("No se encontró ninguna película con ese ID: " + id);
            });
        log.info("Película encontrada: {}", pelicula.getTitulo());
        return convertirADTO(pelicula);
    }

    public List<Pelicula> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            log.warn("Intento de búsqueda con título vacío");
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        log.info("Buscando películas por título: {}", titulo);
        return peliculaRepository.findByTitulo(titulo);
    }

    public PeliculaDTO guardarPelicula(Pelicula pelicula) {
        log.info("Guardando nueva película: {}", pelicula.getTitulo());
        Pelicula guardada = peliculaRepository.save(pelicula);
        log.info("Película creada exitosamente con ID: {}", guardada.getId());
        return convertirADTO(guardada);
    }

    public PeliculaDTO actualizarPelicula(Integer id, Pelicula peliculaActualizada) {
        log.info("Actualizando película con ID: {}", id);
        Pelicula pelicula = peliculaRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No se encontró película con ID {} para actualizar", id);
                return new RuntimeException("La película con ID " + id + " no existe.");
            });
        pelicula.setTitulo(peliculaActualizada.getTitulo());
        pelicula.setDescripcion(peliculaActualizada.getDescripcion());
        pelicula.setDuracion(peliculaActualizada.getDuracion());
        pelicula.setAnioEstreno(peliculaActualizada.getAnioEstreno());
        pelicula.setEstado(peliculaActualizada.getEstado());
        Pelicula guardada = peliculaRepository.save(pelicula);
        log.info("Película con ID {} actualizada exitosamente", id);
        return convertirADTO(guardada);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando película con ID: {}", id);
        Pelicula pelicula = peliculaRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No se puede eliminar. Película con ID {} no existe", id);
                return new RuntimeException("No se puede eliminar. La película con ID " + id + " no existe.");
            });
        peliculaRepository.delete(pelicula);
        log.info("Película '{}' eliminada exitosamente", pelicula.getTitulo());
    }

    public List<Pelicula> buscarPorAnio(Integer anio) {
        if (anio <= 0) {
            log.warn("Año inválido recibido: {}", anio);
            throw new IllegalArgumentException("El año debe ser mayor a 0");
        }
        log.info("Buscando películas del año: {}", anio);
        return peliculaRepository.findByAnioEstreno(anio);
    }

    public List<Pelicula> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            log.warn("Intento de búsqueda con estado vacío");
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        if (!"EN_CARTELERA".equals(estado) && !"PREVENTA".equals(estado)) {
            log.warn("Estado inválido recibido: {}", estado);
            throw new IllegalArgumentException("Estado inválido: debe ser EN_CARTELERA o PREVENTA");
        }
        log.info("Buscando películas con estado: {}", estado);
        return peliculaRepository.findByEstado(estado);
    }

    private PeliculaDTO convertirADTO(Pelicula pelicula) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(pelicula.getId());
        dto.setTitulo(pelicula.getTitulo());
        dto.setDescripcion(pelicula.getDescripcion());
        dto.setDuracion(pelicula.getDuracion());
        dto.setAnioEstreno(pelicula.getAnioEstreno());
        dto.setEstado(pelicula.getEstado());

        // Géneros
        if (pelicula.getGeneros() != null && !pelicula.getGeneros().isEmpty()) {
            dto.setNombreGeneros(pelicula.getGeneros().get(0).getGenero().getNombre());
        } else {
            dto.setNombreGeneros("Sin género asignado");
        }

        // Directores
        if (pelicula.getDirectores() != null && !pelicula.getDirectores().isEmpty()) {
            dto.setNombreDirectores(pelicula.getDirectores().get(0).getDirector().getNombre());
        } else {
            dto.setNombreDirectores("Director desconocido");
        }

        // Idiomas
        if (pelicula.getIdiomas() != null && !pelicula.getIdiomas().isEmpty()) {
            dto.setNombreIdiomas(pelicula.getIdiomas().get(0).getIdioma().getNombre());
        } else {
            dto.setNombreIdiomas("Idioma no definido");
        }
        if (pelicula.getSalasPelicula() != null && !pelicula.getSalasPelicula().isEmpty()) {
            dto.setNombreSalasPelicula("Sala ID: " + pelicula.getSalasPelicula().get(0).getSalaId());
        } else {
            dto.setNombreSalasPelicula("Sala no asignada");
        }

        return dto;
    }
}