package com.sala.salas.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sala.salas.DTO.TipoAsientoDTO;
import com.sala.salas.assemblers.TipoAsientoModelAssembler;
import com.sala.salas.service.TipoAsientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tipo-asiento")
@Tag(name = "Tipos de Asiento V2", description = "Operaciones HATEOAS sobre tipos de asiento")
public class TipoAsientoControllerV2 {

    @Autowired
    private TipoAsientoService tipoAsientoService;

    @Autowired
    private TipoAsientoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los tipos de asiento con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<TipoAsientoDTO>>> listar() {
        List<EntityModel<TipoAsientoDTO>> tipos = tipoAsientoService.listar()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (tipos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        CollectionModel<EntityModel<TipoAsientoDTO>> collection = CollectionModel.of(tipos,
            linkTo(methodOn(TipoAsientoControllerV2.class).listar()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de asiento por ID con links HATEOAS")
    public ResponseEntity<EntityModel<TipoAsientoDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(assembler.toModel(tipoAsientoService.buscarPorId(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un tipo de asiento")
    public ResponseEntity<EntityModel<TipoAsientoDTO>> crear(@RequestBody TipoAsientoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(assembler.toModel(tipoAsientoService.guardar(dto)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de asiento")
    public ResponseEntity<EntityModel<TipoAsientoDTO>> actualizar(
            @PathVariable Integer id, @RequestBody TipoAsientoDTO dto) {
        try {
            return ResponseEntity.ok(assembler.toModel(tipoAsientoService.actualizar(id, dto)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de asiento")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tipoAsientoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
