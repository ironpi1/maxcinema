package com.maxcinema.maxcinema.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maxcinema.maxcinema.DTO.GenerosDTO;
import com.maxcinema.maxcinema.model.Generos;
import com.maxcinema.maxcinema.service.GenerosService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/generos-pelicula")
@Slf4j
public class GenerosController {

    @Autowired
    private GenerosService generosService;

    @GetMapping
    public ResponseEntity<List<GenerosDTO>> listar() {
        log.debug("GET /generos-pelicula");
        return ResponseEntity.ok(generosService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenerosDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /generos-pelicula/{}", id);
        try {
            return ResponseEntity.ok(generosService.buscarPorId(id));
        } catch (RuntimeException e) {
            log.warn("Relación género-película {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pelicula/{peliculaId}")
    public ResponseEntity<List<GenerosDTO>> buscarPorPelicula(@PathVariable Integer peliculaId) {
        log.debug("GET /generos-pelicula/pelicula/{}", peliculaId);
        try {
            return ResponseEntity.ok(generosService.buscarPorPelicula(peliculaId));
        } catch (RuntimeException e) {
            log.warn("No se encontraron géneros para película {}", peliculaId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/genero/{generoId}")
    public ResponseEntity<List<GenerosDTO>> buscarPorGenero(@PathVariable Integer generoId) {
        log.debug("GET /generos-pelicula/genero/{}", generoId);
        try {
            return ResponseEntity.ok(generosService.buscarPorGenero(generoId));
        } catch (RuntimeException e) {
            log.warn("No se encontraron películas para género {}", generoId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GenerosDTO> guardar(@RequestBody Generos generos) {
        log.debug("POST /generos-pelicula");
        return ResponseEntity.status(201).body(generosService.guardar(generos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenerosDTO> actualizar(@PathVariable Integer id, @RequestBody Generos generos) {
        log.debug("PUT /generos-pelicula/{}", id);
        try {
            return ResponseEntity.ok(generosService.actualizar(id, generos));
        } catch (RuntimeException e) {
            log.error("Error al actualizar relación género-película {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /generos-pelicula/{}", id);
        try {
            return ResponseEntity.ok(generosService.eliminar(id));
        } catch (RuntimeException e) {
            log.error("Error al eliminar relación género-película {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
