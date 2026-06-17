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

import com.sala.salas.DTO.TiposSalasDTO;
import com.sala.salas.model.TiposSalas;
import com.sala.salas.service.TiposSalasService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tipos-salas")
@Tag(name = "TiposSalas", description = "Relación entre salas y tipos de sala")
public class TiposSalasController {

    @Autowired
    private TiposSalasService tiposSalasService;

    @GetMapping
    public ResponseEntity<List<TiposSalasDTO>> listar() {
        List<TiposSalasDTO> lista = tiposSalasService.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiposSalasDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(tiposSalasService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TiposSalasDTO> crear(@RequestBody TiposSalas entidad) {
        try {
            return ResponseEntity.ok(tiposSalasService.guardar(entidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiposSalasDTO> actualizar(@PathVariable Integer id, @RequestBody TiposSalas entidad) {
        try {
            return ResponseEntity.ok(tiposSalasService.actualizar(id, entidad));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tiposSalasService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}