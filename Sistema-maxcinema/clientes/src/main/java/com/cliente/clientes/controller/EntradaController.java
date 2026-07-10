package com.cliente.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.cliente.clientes.DTO.EntradasDTO;
import com.cliente.clientes.model.Entrada;
import com.cliente.clientes.service.EntradaService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1/entradas")
@Slf4j
public class EntradaController {
    @Autowired
    private EntradaService entradaService;

    @GetMapping
    public ResponseEntity<List<EntradasDTO>> todosLasEntradas() {
        List<EntradasDTO> entradas = entradaService.obtenerTodos();
        if (entradas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entradas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradasDTO> buscarPorId(@PathVariable Integer id) {
        try {
            EntradasDTO entrada = entradaService.buscarPorId(id);
            return new ResponseEntity<>(entrada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntradasDTO> agregarEntrada(@RequestBody Entrada entrada) {
        try {
            Entrada guardado = entradaService.crearEntrada(entrada);
            return new ResponseEntity<>(entradaService.convertirADTO(guardado), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradasDTO> actualizarEntrada(@PathVariable Integer id, @RequestBody Entrada entrada) {
        log.debug("PUT /entradas/{}",id);
        return ResponseEntity.ok(entradaService.editarEntrada(id, entrada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarEntrada(@PathVariable Integer id) {
        String resultado = entradaService.cancelarEntrada(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<EntradasDTO> actualizarClienteParcial(@PathVariable Integer id, @RequestBody Entrada entrada){
        try {
            EntradasDTO newEntrada = entradaService.editarEntrada(id, entrada);
            return new ResponseEntity<>(newEntrada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}