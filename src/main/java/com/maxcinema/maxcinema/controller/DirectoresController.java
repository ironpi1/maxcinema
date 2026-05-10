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
import com.maxcinema.maxcinema.dto.DirectoresDTO;
import com.maxcinema.maxcinema.model.Directores;
import com.maxcinema.maxcinema.service.DirectoresService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/directores-pelicula")
@Slf4j
public class DirectoresController {

    @Autowired
    private DirectoresService directoresService;

    @GetMapping
    public ResponseEntity<List<DirectoresDTO>> listar() {
        log.debug("GET /directores-pelicula");
        return ResponseEntity.ok(directoresService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectoresDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /directores-pelicula/{}", id);
        return ResponseEntity.ok(directoresService.buscarPorId(id));
    }

    @GetMapping("/pelicula/{peliculaId}")
    public ResponseEntity<List<DirectoresDTO>> buscarPorPelicula(@PathVariable Integer peliculaId) {
        log.debug("GET /directores-pelicula/pelicula/{}", peliculaId);
        return ResponseEntity.ok(directoresService.buscarDirectoresPorPelicula(peliculaId));
    }

    @GetMapping("/director/{directorId}")
    public ResponseEntity<List<DirectoresDTO>> buscarPorDirector(@PathVariable Integer directorId) {
        log.debug("GET /directores-pelicula/director/{}", directorId);
        return ResponseEntity.ok(directoresService.buscarPeliculasPorDirector(directorId));
    }

    @PostMapping
    public ResponseEntity<DirectoresDTO> guardar(@RequestBody @Valid Directores directores) {
        log.debug("POST /directores-pelicula");
        return ResponseEntity.status(HttpStatus.CREATED).body(directoresService.guardar(directores));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectoresDTO> actualizar(@PathVariable Integer id, @RequestBody @Valid Directores directores) {
        log.debug("PUT /directores-pelicula/{}", id);
        return ResponseEntity.ok(directoresService.actualizar(id, directores));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /directores-pelicula/{}", id);
        return ResponseEntity.ok(directoresService.eliminar(id));
    }
}