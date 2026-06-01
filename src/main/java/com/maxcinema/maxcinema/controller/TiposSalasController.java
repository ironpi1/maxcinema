package com.maxcinema.maxcinema.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxcinema.maxcinema.DTO.TiposSalasDto;
import com.maxcinema.maxcinema.model.TiposSalas;
import com.maxcinema.maxcinema.service.TiposSalasService;

@RestController
@RequestMapping("/api/v1/tipos_de_sala")
public class TiposSalasController {
    @Autowired
    private TiposSalasService tiposSalasService;

     @GetMapping
    public ResponseEntity<List<TiposSalasDto>> listarTiposSalas() {
        List<TiposSalasDto> tiposSalas = tiposSalasService.ListarTiposSalas();
        if (tiposSalas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tiposSalas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiposSalasDto> buscarPorId(@PathVariable Integer id) {
        try {
            TiposSalasDto tiposSalas = tiposSalasService.buscarTiposSalasPorId(id);
            return new ResponseEntity<>(tiposSalas, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TiposSalasDto> guardarTiposSalas(@RequestBody TiposSalas tiposSalas) {
        try {
            TiposSalasDto guardado = tiposSalasService.guardarTiposSalas(tiposSalas);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TiposSalasDto> editarTiposSalas(@PathVariable Integer id, @RequestBody TiposSalas tiposSalas) {
        try {
            TiposSalasDto editado = tiposSalasService.actualizar(id, tiposSalas);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiposSalasDto> actualizarTiposSalas(@PathVariable Integer id, @RequestBody TiposSalas tiposSalas) {
        try {
            TiposSalasDto newTiposSalas = tiposSalasService.actualizar(id, tiposSalas);
            return new ResponseEntity<>(newTiposSalas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTiposSalas(@PathVariable Integer id) {
        String resultado = tiposSalasService.eliminarTiposDeSalas(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
