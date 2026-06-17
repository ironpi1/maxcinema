package com.sala.salas.controller;

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

import com.sala.salas.DTO.TipoAsientoDTO;
import com.sala.salas.service.TipoAsientoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tipo-asiento")
@Tag(name = "TipoAsiento", description = "Operaciones relacionadas con los tipos de asiento")
public class TipoAsientoController {

    @Autowired
    private TipoAsientoService tipoAsientoService;

    @GetMapping
    public ResponseEntity<List<TipoAsientoDTO>> listar() {
        List<TipoAsientoDTO> lista = tipoAsientoService.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAsientoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(tipoAsientoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoAsientoDTO> crear(@RequestBody TipoAsientoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoAsientoService.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoAsientoDTO> actualizar(@PathVariable Integer id, @RequestBody TipoAsientoDTO dto) {
        try {
            return ResponseEntity.ok(tipoAsientoService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tipoAsientoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}