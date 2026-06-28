package com.cine.cines.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.cines.DTO.RegionDTO;
import com.cine.cines.assemblers.RegionModelAssembler;
import com.cine.cines.model.Region;
import com.cine.cines.service.RegionService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/regiones")
@Tag(name = "Regiones V2", description = "Operaciones HATEOAS sobre regiones")
public class RegionControllerV2 {
    
    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las regiones con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<RegionDTO>>> listarRegiones() {
        List<EntityModel<RegionDTO>> regiones = regionService.listarRegion()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (regiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        CollectionModel<EntityModel<RegionDTO>> collection = CollectionModel.of(regiones,
            linkTo(methodOn(RegionControllerV2.class).listarRegiones()).withSelfRel());
            
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar region por ID con links HATEOAS")
    public ResponseEntity<EntityModel<RegionDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            RegionDTO region = regionService.buscarRegionPorId(id);
            return ResponseEntity.ok(assembler.toModel(region));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva region con links HATEOAS")
    public ResponseEntity<EntityModel<RegionDTO>> guardarRegion(@RequestBody Region region) {
        try {
            RegionDTO guardado = regionService.guardarRegion(region);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar una region existente con links HATEOAS")
    public ResponseEntity<EntityModel<RegionDTO>> editarRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            RegionDTO editado = regionService.actualizarRegion(id, region);
            return ResponseEntity.ok(assembler.toModel(editado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una region existente con links HATEOAS")
    public ResponseEntity<EntityModel<RegionDTO>> actualizarRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            RegionDTO newRegion = regionService.actualizarRegion(id, region);
            return ResponseEntity.ok(assembler.toModel(newRegion));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

