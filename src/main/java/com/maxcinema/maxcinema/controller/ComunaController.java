package com.maxcinema.maxcinema.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxcinema.maxcinema.DTO.ComunaDto;
import com.maxcinema.maxcinema.model.Comuna;
import com.maxcinema.maxcinema.service.ComunaService;

@RestController
@RequestMapping("/api/v1/comuna")
public class ComunaController {
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaDto>> listarComunas() {
        List<ComunaDto> comunas = comunaService.listarComuna();
        if (comunas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comunas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaDto> buscarPorId(@PathVariable Integer id) {
        try {
            ComunaDto comuna = comunaService.buscarComunaPorId(id);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ComunaDto> guardarComuna(@RequestBody Comuna comuna) {
        try {
            ComunaDto guardado = comunaService.guardarComuna(comuna);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ComunaDto> editarComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            ComunaDto editado = comunaService.actualizarComuna(id, comuna);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunaDto> actualizarComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            ComunaDto newComuna = comunaService.actualizarComuna(id, comuna);
            return new ResponseEntity<>(newComuna, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarComuna(@PathVariable Integer id) {
        String resultado = comunaService.eliminarComuna(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
