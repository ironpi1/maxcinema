package com.pelicula.peliculas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pelicula.peliculas.DTO.SalasPeliculaDTO;
import com.pelicula.peliculas.model.SalasPelicula;
import com.pelicula.peliculas.service.SalasPeliculaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/salas-pelicula")
@Slf4j
public class SalasPeliculaController {

    @Autowired
    private SalasPeliculaService salasPeliculaService;

    @GetMapping
    public ResponseEntity<List<SalasPeliculaDTO>> listar() {
        log.debug("GET /salas-pelicula");
        return ResponseEntity.ok(salasPeliculaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalasPeliculaDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /salas-pelicula/{}", id);
        return ResponseEntity.ok(salasPeliculaService.buscarPorId(id));
    }

    @GetMapping("/pelicula/{peliculaId}")
    public ResponseEntity<List<SalasPeliculaDTO>> buscarPorPelicula(@PathVariable Integer peliculaId) {
        log.debug("GET /salas-pelicula/pelicula/{}", peliculaId);
        return ResponseEntity.ok(salasPeliculaService.buscarPorPelicula(peliculaId));
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<SalasPeliculaDTO>> buscarPorSala(@PathVariable Integer salaId) {
        log.debug("GET /salas-pelicula/sala/{}", salaId);
        return ResponseEntity.ok(salasPeliculaService.buscarPorSala(salaId));
    }

    @PostMapping
    public ResponseEntity<SalasPeliculaDTO> guardar(@RequestBody @Valid SalasPelicula salasPelicula) {
        log.debug("POST /salas-pelicula");
        return ResponseEntity.status(HttpStatus.CREATED).body(salasPeliculaService.guardar(salasPelicula));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalasPeliculaDTO> actualizar(@PathVariable Integer id, @RequestBody @Valid SalasPelicula salasPelicula) {
        log.debug("PUT /salas-pelicula/{}", id);
        return ResponseEntity.ok(salasPeliculaService.actualizar(id, salasPelicula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /salas-pelicula/{}", id);
        return ResponseEntity.ok(salasPeliculaService.eliminar(id));
    }
}
