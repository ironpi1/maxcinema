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

import com.sala.salas.DTO.SalaDTO;
import com.sala.salas.service.SalaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sala")
@Tag(name = "Salas", description = "Operaciones relacionadas con las salas de cine")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    @Operation(summary = "Listar todas las salas")
    public ResponseEntity<List<SalaDTO>> listar() {
        List<SalaDTO> salas = salaService.listarSalas();
        if (salas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID")
    public ResponseEntity<SalaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(salaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una sala")
    public ResponseEntity<SalaDTO> crear(@RequestBody SalaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(salaService.guardarSala(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sala")
    public ResponseEntity<SalaDTO> actualizar(@PathVariable Integer id, @RequestBody SalaDTO dto) {
        try {
            return ResponseEntity.ok(salaService.actualizarSala(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sala")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            salaService.eliminarSala(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}