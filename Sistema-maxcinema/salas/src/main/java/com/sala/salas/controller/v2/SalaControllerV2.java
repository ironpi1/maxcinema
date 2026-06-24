package com.sala.salas.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sala.salas.DTO.SalaDTO;
import com.sala.salas.assemblers.SalaModelAssembler;
import com.sala.salas.service.SalaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/sala")
@Tag(name = "Salas V2", description = "Operaciones HATEOAS sobre salas de cine")
public class SalaControllerV2 {

    @Autowired
    private SalaService salaService;

    @Autowired
    private SalaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las salas con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<SalaDTO>>> listar() {
        List<EntityModel<SalaDTO>> salas = salaService.listarSalas()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (salas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CollectionModel<EntityModel<SalaDTO>> collection = CollectionModel.of(salas,
            linkTo(methodOn(SalaControllerV2.class).listar()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID con links HATEOAS")
    public ResponseEntity<EntityModel<SalaDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            SalaDTO sala = salaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(sala));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una sala")
    public ResponseEntity<EntityModel<SalaDTO>> crear(@RequestBody SalaDTO dto) {
        try {
            SalaDTO guardada = salaService.guardarSala(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardada));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sala")
    public ResponseEntity<EntityModel<SalaDTO>> actualizar(
            @PathVariable Integer id, @RequestBody SalaDTO dto) {
        try {
            SalaDTO actualizada = salaService.actualizarSala(id, dto);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sala")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            salaService.eliminarSala(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
