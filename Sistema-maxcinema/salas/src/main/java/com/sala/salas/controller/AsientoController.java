package com.sala.salas.controller;

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

import com.sala.salas.DTO.AsientoDTO;
import com.sala.salas.model.Asiento;
import com.sala.salas.service.AsientoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/asientos")
@Tag(name = "Asientos", description = "Operaciones relacionadas con los asientos de las salas")
public class AsientoController {

    @Autowired
    private AsientoService asientoService;

    @GetMapping
    public ResponseEntity<List<AsientoDTO>> listar() {
        List<AsientoDTO> lista = asientoService.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsientoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(asientoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AsientoDTO> crear(@RequestBody Asiento asiento) {
        try {
            return ResponseEntity.ok(asientoService.guardar(asiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsientoDTO> actualizar(@PathVariable Integer id, @RequestBody Asiento asiento) {
        try {
            return ResponseEntity.ok(asientoService.actualizar(id, asiento));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            asientoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}