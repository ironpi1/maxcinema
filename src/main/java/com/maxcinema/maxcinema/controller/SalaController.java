package com.maxcinema.maxcinema.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxcinema.maxcinema.DTO.SalaDto;
import com.maxcinema.maxcinema.model.sala;
import com.maxcinema.maxcinema.service.SalaService;

@RestController
@RequestMapping("/api/v1/sala")
public class SalaController {
    @Autowired
    private SalaService salaService;

    @GetMapping("/{id}")
    public ResponseEntity<SalaDto> buscarPorId(@PathVariable Integer id) {
        try {
            SalaDto sala = salaService.buscarSalaPorId(id);
            return new ResponseEntity<>(sala, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SalaDto> guardarSala(@RequestBody sala sala) {
        try {
            SalaDto guardado = salaService.guardarSala(sala);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalaDto> editarSala(@PathVariable Integer id, @RequestBody sala sala) {
        try {
            SalaDto editado = salaService.actualizarSala(id, sala);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDto> actualizarSala(@PathVariable Integer id, @RequestBody sala sala) {
        try {
            SalaDto newSala = salaService.actualizarSala(id, sala);
            return new ResponseEntity<>(newSala, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSala(@PathVariable Integer id) {
        String resultado = salaService.eliminarSala(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
