package com.cine.cines.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.cines.DTO.ComunaDTO;
import com.cine.cines.model.Comuna;
import com.cine.cines.service.ComunaService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarComunas() {
        List<ComunaDTO> comunas = comunaService.listarComuna();
        if (comunas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comunas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            ComunaDTO comuna = comunaService.buscarComunaPorId(id);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ComunaDTO> guardarComuna(@RequestBody Comuna comuna) {
        try {
            ComunaDTO guardado = comunaService.guardarComuna(comuna);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ComunaDTO> editarComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            ComunaDTO editado = comunaService.actualizarComuna(id, comuna);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunaDTO> actualizarComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            ComunaDTO newComuna = comunaService.actualizarComuna(id, comuna);
            return new ResponseEntity<>(newComuna, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}