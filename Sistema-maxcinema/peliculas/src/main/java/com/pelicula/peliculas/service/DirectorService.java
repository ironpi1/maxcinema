package com.pelicula.peliculas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelicula.peliculas.DTO.DirectorDTO;
import com.pelicula.peliculas.model.Director;
import com.pelicula.peliculas.repository.DirectorRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    // Obtener todos los directores
    public List<DirectorDTO> obtenerTodos() {
        return directorRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    // Buscar por ID
    public DirectorDTO buscarPorId(Integer id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún director con ID: " + id));
        return convertirADTO(director);
    }

    // Buscar por nombre
    public List<DirectorDTO> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        log.info("Buscando directores por nombre: {}", nombre);
        List<Director> directores = directorRepository.findByNombre(nombre);

        List<DirectorDTO> resultado = new ArrayList<>();
        for (Director director : directores) {
            resultado.add(convertirADTO(director));
        }
        return resultado;
    }

    // Guardar nuevo director
    public DirectorDTO guardarDirector(Director director) {
        Director guardado = directorRepository.save(director);
        return convertirADTO(guardado);
    }

    // Actualizar director existente
    public DirectorDTO actualizarDirector(Integer id, Director directorActualizado) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar. El director con ID " + id + " no existe."));

        director.setNombre(directorActualizado.getNombre());
        director.setNacionalidad(directorActualizado.getNacionalidad());

        Director actualizado = directorRepository.save(director);
        log.info("Director actualizado: {}", actualizado.getNombre());
        return convertirADTO(actualizado);
    }

    // Eliminar director
    public String eliminar(Integer id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar. El director con ID " + id + " no existe."));
        directorRepository.delete(director);
        return "El director '" + director.getNombre() + "' ha sido eliminado exitosamente.";
    }

    private DirectorDTO convertirADTO(Director director) {
        DirectorDTO dto = new DirectorDTO();
        dto.setId(director.getId());
        dto.setNombre(director.getNombre());
        dto.setNacionalidad(director.getNacionalidad());
        return dto;
    }

}
