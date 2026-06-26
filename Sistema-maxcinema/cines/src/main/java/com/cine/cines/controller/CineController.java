package com.cine.cines.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.cines.DTO.CineDTO;
import com.cine.cines.model.Cine;
import com.cine.cines.service.CineService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/cines")
public class CineController {
    @Autowired
    private CineService cineService;

    @GetMapping
    public ResponseEntity<List<CineDTO>> listarCines() {
        List<CineDTO> cines = cineService.listarCine();
        if (cines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CineDTO> buscarPorId(@PathVariable Integer id) {
        try {
            CineDTO cine = cineService.buscarCinePorId(id);
            return new ResponseEntity<>(cine, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CineDTO> agregarCine(@RequestBody CineDTO cine) {
        try {
            CineDTO guardado = cineService.guardarCine(cine);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CineDTO> editarCine(@PathVariable Integer id, @RequestBody CineDTO cine) {
        try {
            CineDTO editado = cineService.actualizarCine(id, cine);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CineDTO> actualizarCine(@PathVariable Integer id, @RequestBody CineDTO cine) {
        try {
            CineDTO newCine = cineService.actualizarCine(id, cine);
            return new ResponseEntity<>(newCine, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCine(@PathVariable Integer id) {
        String resultado = cineService.eliminarCine(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
