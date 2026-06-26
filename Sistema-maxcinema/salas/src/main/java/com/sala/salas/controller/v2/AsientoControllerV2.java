package com.sala.salas.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sala.salas.DTO.AsientoDTO;
import com.sala.salas.assemblers.AsientoModelAssembler;
import com.sala.salas.model.Asiento;
import com.sala.salas.service.AsientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/asientos")
@Tag(name = "Asientos V2", description = "Operaciones HATEOAS sobre asientos")
public class AsientoControllerV2 {

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private AsientoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los asientos con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<AsientoDTO>>> listar() {
        List<EntityModel<AsientoDTO>> asientos = asientoService.listar()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (asientos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        CollectionModel<EntityModel<AsientoDTO>> collection = CollectionModel.of(asientos,
            linkTo(methodOn(AsientoControllerV2.class).listar()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asiento por ID con links HATEOAS")
    public ResponseEntity<EntityModel<AsientoDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(assembler.toModel(asientoService.buscarPorId(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un asiento")
    public ResponseEntity<EntityModel<AsientoDTO>> crear(@RequestBody Asiento asiento) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(assembler.toModel(asientoService.guardar(asiento)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un asiento")
    public ResponseEntity<EntityModel<AsientoDTO>> actualizar(
            @PathVariable Integer id, @RequestBody Asiento asiento) {
        try {
            return ResponseEntity.ok(assembler.toModel(asientoService.actualizar(id, asiento)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un asiento")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            asientoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
