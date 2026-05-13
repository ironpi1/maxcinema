package com.maxcinema.maxcinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxcinema.maxcinema.DTO.TipoAsientoDTO;
import com.maxcinema.maxcinema.model.TipoAsiento;
import com.maxcinema.maxcinema.service.TipoAsientoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/tipos-asiento")
@Slf4j
public class TipoAsientoController {
    @Autowired
    private TipoAsientoService tipoAsientoService;

    @GetMapping
    public ResponseEntity<List<TipoAsientoDTO>> todosLosTiposDeAsiento() {
        List<TipoAsientoDTO> tipos = tipoAsientoService.obtenerTodos();
        if (tipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAsientoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            TipoAsientoDTO tipo = tipoAsientoService.buscarPorId(id);
            return new ResponseEntity<>(tipo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoAsiento> agregarTipoDeAsiento(@RequestBody TipoAsiento tipoAsiento) {
        try {
            TipoAsiento guardado = tipoAsientoService.crearTipoAsiento(tipoAsiento);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoAsiento> actualizarTipoDeAsiento(@PathVariable Integer id, @RequestBody TipoAsiento tipoAsiento) {
        try{
            TipoAsiento newTipoAsiento = tipoAsientoService.editarTipoAsiento(id, tipoAsiento);
            return new ResponseEntity<>(newTipoAsiento, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
