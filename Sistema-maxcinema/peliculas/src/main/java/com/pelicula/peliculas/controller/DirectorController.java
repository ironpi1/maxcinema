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

import com.pelicula.peliculas.DTO.DirectorDTO;
import com.pelicula.peliculas.model.Director;
import com.pelicula.peliculas.service.DirectorService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/directores")
@Slf4j
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<DirectorDTO>> listar() {
        log.debug("GET /directores");
        return ResponseEntity.ok(directorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /directores/{}", id);
        return ResponseEntity.ok(directorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DirectorDTO> guardar(@RequestBody @Valid Director director) {
        log.debug("POST /directores");
        return ResponseEntity.status(HttpStatus.CREATED).body(directorService.guardarDirector(director));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorDTO> actualizar(@PathVariable Integer id, @RequestBody @Valid Director director) {
        log.debug("PUT /directores/{}", id);
        return ResponseEntity.ok(directorService.actualizarDirector(id, director));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /directores/{}", id);
        return ResponseEntity.ok(directorService.eliminar(id));
    }
}