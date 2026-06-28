package com.cine.cines.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
import com.cine.cines.assemblers.CineModelAssembler;
import com.cine.cines.service.CineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/cines")
@Tag(name = "Cines V2", description = "Operaciones HATEOAS sobre cines")  
public class CineControllerV2 {
    @Autowired
    private CineService cineService;

    @Autowired
    private CineModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los cines con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CineDTO>>> listarCines() {
        List<EntityModel<CineDTO>> cines = cineService.listarCine()
            .stream()    
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (cines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CollectionModel<EntityModel<CineDTO>> collecion = CollectionModel.of(cines,
            linkTo(methodOn(CineControllerV2.class).listarCines()).withSelfRel());

        return ResponseEntity.ok(collecion);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cine por ID con links HATEOAS")
    public ResponseEntity<EntityModel<CineDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            CineDTO cine = cineService.buscarCinePorId(id);
            return ResponseEntity.ok(assembler.toModel(cine));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo cine con links HATEOAS")
    public ResponseEntity<EntityModel<CineDTO>> agregarCine(@RequestBody CineDTO cine) {
        try {
            CineDTO guardado = cineService.guardarCine(cine);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar un cine existente con links HATEOAS")
    public ResponseEntity<EntityModel<CineDTO>> editarCine(@PathVariable Integer id, @RequestBody CineDTO cine) {
        try {
            CineDTO editado = cineService.actualizarCine(id, cine);
            return ResponseEntity.ok(assembler.toModel(editado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cine existente con links HATEOAS")
    public ResponseEntity<EntityModel<CineDTO>> actualizarCine(@PathVariable Integer id, @RequestBody CineDTO cine) {
        try {
            CineDTO newCine = cineService.actualizarCine(id, cine);
            return ResponseEntity.ok(assembler.toModel(newCine));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cine existente con links HATEOAS")
    public ResponseEntity<String> eliminarCine(@PathVariable Integer id) {
        try {
            String resultado = cineService.eliminarCine(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

