package com.maxcinema.maxcinema.controller;

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

import com.maxcinema.maxcinema.dto.PeliculaDTO;
import com.maxcinema.maxcinema.model.Pelicula;
import com.maxcinema.maxcinema.service.PeliculaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/peliculas")
@Slf4j
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> listar() {
        log.debug("GET /peliculas");
        return ResponseEntity.ok(peliculaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /peliculas/{}", id);
        return ResponseEntity.ok(peliculaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> guardar(@RequestBody @Valid Pelicula pelicula) {
        log.debug("POST /peliculas");
        PeliculaDTO dto = peliculaService.guardarPelicula(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> actualizar(@PathVariable Integer id, @RequestBody @Valid Pelicula pelicula) {
        log.debug("PUT /peliculas/{}", id);
        return ResponseEntity.ok(peliculaService.actualizarPelicula(id, pelicula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /peliculas/{}", id);
        return ResponseEntity.ok(peliculaService.eliminar(id));
    }
}
