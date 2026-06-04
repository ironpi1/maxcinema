package com.maxcinema.maxcinema.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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


import com.maxcinema.maxcinema.DTO.CineDto;
import com.maxcinema.maxcinema.model.Cine;
import com.maxcinema.maxcinema.service.CineService;

@RestController
@RequestMapping("/api/v1/cine")
public class CineController {
    @Autowired
    private CineService cineService;

    @GetMapping
    public ResponseEntity<List<CineDto>> listarCines() {
        List<CineDto> cines = cineService.listarCine();
        if (cines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CineDto> buscarPorId(@PathVariable Integer id) {
        try {
            CineDto cine = cineService.buscarCinePorId(id);
            return new ResponseEntity<>(cine, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CineDto> agregarCine(@RequestBody Cine cine) {
        try {
            CineDto guardado = cineService.guardarCine(cine);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CineDto> editarCine(@PathVariable Integer id, @RequestBody Cine cine) {
        try {
            CineDto editado = cineService.actualizarCine(id, cine);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CineDto> actualizarCine(@PathVariable Integer id, @RequestBody Cine cine) {
        try {
            CineDto newCine = cineService.actualizarCine(id, cine);
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
