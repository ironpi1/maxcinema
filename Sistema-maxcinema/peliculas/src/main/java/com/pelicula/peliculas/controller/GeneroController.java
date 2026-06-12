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

import com.pelicula.peliculas.DTO.GeneroDTO;
import com.pelicula.peliculas.model.Genero;
import com.pelicula.peliculas.service.GeneroService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/generos")
@Slf4j
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<GeneroDTO>> listar() {
        log.debug("GET /generos");
        return ResponseEntity.ok(generoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /generos/{}", id);
        return ResponseEntity.ok(generoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<GeneroDTO> guardar(@RequestBody @Valid Genero genero) {
        log.debug("POST /generos");
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.guardar(genero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> actualizar(@PathVariable Integer id, @RequestBody @Valid Genero genero) {
        log.debug("PUT /generos/{}", id);
        return ResponseEntity.ok(generoService.actualizar(id, genero));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /generos/{}", id);
        return ResponseEntity.ok(generoService.eliminar(id));
    }
}
