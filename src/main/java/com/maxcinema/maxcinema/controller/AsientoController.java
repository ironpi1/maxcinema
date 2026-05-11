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

import com.maxcinema.maxcinema.DTO.AsientoDTO;
import com.maxcinema.maxcinema.model.Asiento;
import com.maxcinema.maxcinema.service.AsientoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/asientos")
@Slf4j
public class AsientoController {
    @Autowired
    private AsientoService asientoService;
    
    @GetMapping
    public ResponseEntity<List<AsientoDTO>> todosLosAsientos() {
        List<AsientoDTO> asientos = asientoService.obtenerTodos();
        if (asientos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(asientos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsientoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            AsientoDTO asiento = asientoService.buscarPorId(id);
            return new ResponseEntity<>(asiento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Asiento> agregarAsiento(@RequestBody Asiento asiento) {
        try {
            Asiento guardado = asientoService.guardarAsiento(asiento);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asiento> actualizarAsiento(@PathVariable Integer id, @RequestBody Asiento asiento) {
        try{
            Asiento newAsiento = asientoService.editarAsiento(id, asiento);
            return new ResponseEntity<>(newAsiento, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAsiento(@PathVariable Integer id) {
        String resultado = asientoService.eliminarAsiento(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
