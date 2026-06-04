package com.maxcinema.maxcinema.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;


import com.maxcinema.maxcinema.DTO.SalaDto;
import com.maxcinema.maxcinema.model.Sala;
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
    public ResponseEntity<SalaDto> guardarSala(@RequestBody Sala sala) {
        try {
            SalaDto guardado = salaService.guardarSala(sala);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalaDto> editarSala(@PathVariable Integer id, @RequestBody Sala sala) {
        try {
            SalaDto editado = salaService.actualizarSala(id, sala);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDto> actualizarSala(@PathVariable Integer id, @RequestBody Sala sala) {
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
