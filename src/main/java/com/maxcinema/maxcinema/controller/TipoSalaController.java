package com.maxcinema.maxcinema.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxcinema.maxcinema.DTO.TipoSalaDto;
import com.maxcinema.maxcinema.model.TipoSala;
import com.maxcinema.maxcinema.service.TipoSalaService;

@RestController
@RequestMapping("/api/v1/tipo_sala")
public class TipoSalaController {
    @Autowired
    private TipoSalaService tipoSalaService;

    @GetMapping
    public ResponseEntity<List<TipoSalaDto>> listarTipoSalas() {
        List<TipoSalaDto> tipoSalas = tipoSalaService.ListarTipoSala();
        if (tipoSalas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tipoSalas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSalaDto> buscarPorId(@PathVariable Integer id) {
        try {
            TipoSalaDto tipoSala = tipoSalaService.buscarTipoSalaPorId(id);
            return new ResponseEntity<>(tipoSala, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoSalaDto> guardarTipoSala(@RequestBody TipoSala tipoSala) {
        try {
            TipoSalaDto guardado = tipoSalaService.guardarTipoSala(tipoSala);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TipoSalaDto> editarTipoSala(@PathVariable Integer id, @RequestBody TipoSala tipoSala) {
        try {
            TipoSalaDto editado = tipoSalaService.actualizarTipoSala(id, tipoSala);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSalaDto> actualizarTipoSala(@PathVariable Integer id, @RequestBody TipoSala tipoSala) {
        try {
            TipoSalaDto newTipoSala = tipoSalaService.actualizarTipoSala(id, tipoSala);
            return new ResponseEntity<>(newTipoSala, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipoSala(@PathVariable Integer id) {
        String resultado = tipoSalaService.eliminarTipoSala(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
