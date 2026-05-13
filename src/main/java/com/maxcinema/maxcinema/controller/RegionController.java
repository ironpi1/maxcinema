package com.maxcinema.maxcinema.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxcinema.maxcinema.DTO.RegionDto;
import com.maxcinema.maxcinema.model.Region;
import com.maxcinema.maxcinema.service.RegionService;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDto>> listarRegiones() {
        List<RegionDto> regiones = regionService.ListarRegion();
        if (regiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(regiones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDto> buscarPorId(@PathVariable Integer id) {
        try {
            RegionDto region = regionService.buscarRegionPorId(id);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RegionDto> guardarRegion(@RequestBody Region region) {
        try {
            RegionDto guardado = regionService.guardaRegion(region);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RegionDto> editarRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            RegionDto editado = regionService.actualizarRegion(id, region);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDto> actualizarRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            RegionDto newRegion = regionService.actualizarRegion(id, region);
            return new ResponseEntity<>(newRegion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRegion(@PathVariable Integer id) {
        String resultado = regionService.eliminarRegion(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}
