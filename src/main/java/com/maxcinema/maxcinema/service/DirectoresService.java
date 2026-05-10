package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.DTO.DirectoresDTO;
import com.maxcinema.maxcinema.model.Directores;
import com.maxcinema.maxcinema.repository.DirectoresRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DirectoresService {

    @Autowired
    private DirectoresRepository directoresRepository;
    
    public List<DirectoresDTO> obtenerTodos() {
    return directoresRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }

    public DirectoresDTO guardar(Directores directores) {
        Directores guardado = directoresRepository.save(directores);
        log.info("Directores guardado con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public DirectoresDTO buscarPorId(Integer id) {
        Directores directores = directoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existen directores con ID: " + id));
        return convertirADTO(directores);
    }

    public DirectoresDTO actualizar(Integer id, Directores actualizado) {
        Directores directores = directoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar. No existen directores con ID " + id));

        directores.setDirector(actualizado.getDirector());
        directores.setPelicula(actualizado.getPelicula());

        Directores guardado = directoresRepository.save(directores);
        log.info("Directores actualizado con ID {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public String eliminar(Integer id) {
        Directores directores = directoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar. No existen directores con ID " + id));
        directoresRepository.delete(directores);
        log.info("Directores eliminados con ID {}", id);
        return "Directores con ID " + id + " han sido eliminados exitosamente.";
    }

    //Buscar películas por director
    public List<DirectoresDTO> buscarPeliculasPorDirector(Integer directorId) {
        return directoresRepository.findByDirectorId(directorId)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    //Buscar directores por película
    public List<DirectoresDTO> buscarDirectoresPorPelicula(Integer peliculaId) {
        return directoresRepository.findByPeliculaId(peliculaId)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    private DirectoresDTO convertirADTO(Directores relacion) {
        DirectoresDTO dto = new DirectoresDTO();
        dto.setId(relacion.getId());
        dto.setDirectorId(relacion.getDirector().getId());
        dto.setDirectorNombre(relacion.getDirector().getNombre());
        dto.setPeliculaId(relacion.getPelicula().getId());
        dto.setPeliculaTitulo(relacion.getPelicula().getTitulo());
        return dto;
    }
}
