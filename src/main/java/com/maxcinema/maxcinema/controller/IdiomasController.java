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

import com.maxcinema.maxcinema.dto.IdiomasDTO;
import com.maxcinema.maxcinema.model.Idiomas;
import com.maxcinema.maxcinema.service.IdiomasService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/idiomas-pelicula")
@Slf4j
public class IdiomasController {

    @Autowired
    private IdiomasService idiomasService;

    @GetMapping
    public ResponseEntity<List<IdiomasDTO>> listar() {
        log.debug("GET /idiomas-pelicula");
        return ResponseEntity.ok(idiomasService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdiomasDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /idiomas-pelicula/{}", id);
        try {
            return ResponseEntity.ok(idiomasService.buscarPorId(id));
        } catch (RuntimeException e) {
            log.warn("Relación idioma-película {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pelicula/{peliculaId}")
    public ResponseEntity<List<IdiomasDTO>> buscarPorPelicula(@PathVariable Integer peliculaId) {
        log.debug("GET /idiomas-pelicula/pelicula/{}", peliculaId);
        try {
            return ResponseEntity.ok(idiomasService.buscarPorPelicula(peliculaId));
        } catch (RuntimeException e) {
            log.warn("No se encontraron idiomas para película {}", peliculaId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/idioma/{idiomaId}")
    public ResponseEntity<List<IdiomasDTO>> buscarPorIdioma(@PathVariable Integer idiomaId) {
        log.debug("GET /idiomas-pelicula/idioma/{}", idiomaId);
        try {
            return ResponseEntity.ok(idiomasService.buscarPorIdioma(idiomaId));
        } catch (RuntimeException e) {
            log.warn("No se encontraron películas para idioma {}", idiomaId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<IdiomasDTO> guardar(@RequestBody Idiomas idiomas) {
        log.debug("POST /idiomas-pelicula");
        return ResponseEntity.status(201).body(idiomasService.guardar(idiomas));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdiomasDTO> actualizar(@PathVariable Integer id, @RequestBody Idiomas idiomas) {
        log.debug("PUT /idiomas-pelicula/{}", id);
        try {
            return ResponseEntity.ok(idiomasService.actualizar(id, idiomas));
        } catch (RuntimeException e) {
            log.error("Error al actualizar relación idioma-película {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /idiomas-pelicula/{}", id);
        try {
            return ResponseEntity.ok(idiomasService.eliminar(id));
        } catch (RuntimeException e) {
            log.error("Error al eliminar relación idioma-película {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}