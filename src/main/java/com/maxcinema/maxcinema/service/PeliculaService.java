package com.maxcinema.maxcinema.service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.PeliculaDTO;
import com.maxcinema.maxcinema.model.Pelicula;
import com.maxcinema.maxcinema.repository.PeliculaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<PeliculaDTO> obtenerTodas(){
        List<PeliculaDTO> peliculas = new ArrayList<>();
        for(Pelicula pelicula : peliculaRepository.findAll()){
            peliculas.add(convertirADTO(pelicula));
        }
        return peliculas;
    }
    
    public PeliculaDTO buscarPorId(Integer id) {
        Pelicula pelicula = peliculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró ninguna película con ese ID: " + id));
        return convertirADTO(pelicula);
    }

    public List<Pelicula> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        log.info("Buscando películas por título: {}", titulo);
        return peliculaRepository.findByTitulo(titulo);
    }

    public PeliculaDTO guardarPelicula(Pelicula pelicula) {
        Pelicula guardada = peliculaRepository.save(pelicula);
        return convertirADTO(guardada);
    }

    public PeliculaDTO actualizarPelicula(Integer id, Pelicula peliculaActualizada) {
        Pelicula pelicula = peliculaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La película con ID " + id + " no existe."));
        pelicula.setTitulo(peliculaActualizada.getTitulo());
        pelicula.setDescripcion(peliculaActualizada.getDescripcion());
        pelicula.setDuracion(peliculaActualizada.getDuracion());
        pelicula.setAnioEstreno(peliculaActualizada.getAnioEstreno());
        pelicula.setEstado(peliculaActualizada.getEstado());
        Pelicula guardada = peliculaRepository.save(pelicula);
         return convertirADTO(guardada);
}

    public String eliminar(Integer id) {
        try {
            Pelicula pelicula = peliculaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se puede eliminar. La película con ID " + id + " no existe."));
            peliculaRepository.delete(pelicula);
            return "La película '" + pelicula.getTitulo() + "' ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public List<Pelicula> buscarPorAnio(Integer anio) {
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser mayor a 0");
        }
        log.info("Buscando películas del año: {}", anio);
        return peliculaRepository.findByAnioEstreno(anio);
    }

    public List<Pelicula> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
        throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        
        if (!"EN_CARTELERA".equals(estado) && !"PREVENTA".equals(estado)) {
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

    // Salas
        if (pelicula.getSalasPelicula() != null && !pelicula.getSalasPelicula().isEmpty()) {
            dto.setNombreSalasPelicula(pelicula.getSalasPelicula().get(0).getSala().getNombre());
        } else {
            dto.setNombreSalasPelicula("Sala no asignada");
        }
        return dto;

    // Entradas 
        if (pelicula.getEntradas() != null && !pelicula.getEntradas().isEmpty()) {
        dto.setEntradaComprada("Entrada ID: " + pelicula.getEntradas().get(0).getId());
        } else {
             dto.setEntradaComprada("Sin entrada asociada");
        }
    }

    
}
