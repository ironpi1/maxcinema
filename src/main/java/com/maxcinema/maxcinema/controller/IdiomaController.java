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

import com.maxcinema.maxcinema.dto.IdiomaDTO;
import com.maxcinema.maxcinema.model.Idioma;
import com.maxcinema.maxcinema.service.IdiomaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/idiomas")
@Slf4j
public class IdiomaController {

    @Autowired
    private IdiomaService idiomaService;

    @GetMapping
    public ResponseEntity<List<IdiomaDTO>> listar() {
        log.debug("GET /idiomas");
        return ResponseEntity.ok(idiomaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdiomaDTO> buscar(@PathVariable Integer id) {
        log.debug("GET /idiomas/{}", id);
        return ResponseEntity.ok(idiomaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<IdiomaDTO> guardar(@RequestBody @Valid Idioma idioma) {
        log.debug("POST /idiomas");
        return ResponseEntity.status(HttpStatus.CREATED).body(idiomaService.guardar(idioma));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdiomaDTO> actualizar(@PathVariable Integer id, @RequestBody @Valid Idioma idioma) {
        log.debug("PUT /idiomas/{}", id);
        return ResponseEntity.ok(idiomaService.actualizar(id, idioma));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        log.debug("DELETE /idiomas/{}", id);
        return ResponseEntity.ok(idiomaService.eliminar(id));
    }
}