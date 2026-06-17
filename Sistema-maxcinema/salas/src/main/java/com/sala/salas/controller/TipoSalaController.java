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

import com.sala.salas.DTO.TipoSalaDTO;
import com.sala.salas.service.TipoSalaService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tipo-sala")
@Tag(name = "TipoSala", description = "Operaciones relacionadas con los tipos de sala")
public class TipoSalaController {

    @Autowired
    private TipoSalaService tipoSalaService;

    @GetMapping
    public ResponseEntity<List<TipoSalaDTO>> listar() {
        List<TipoSalaDTO> lista = tipoSalaService.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSalaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(tipoSalaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoSalaDTO> crear(@RequestBody TipoSalaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoSalaService.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSalaDTO> actualizar(@PathVariable Integer id, @RequestBody TipoSalaDTO dto) {
        try {
            return ResponseEntity.ok(tipoSalaService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tipoSalaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}