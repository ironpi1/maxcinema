package com.sala.salas.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sala.salas.DTO.TiposSalasDTO;
import com.sala.salas.assemblers.TiposSalasModelAssembler;
import com.sala.salas.model.TiposSalas;
import com.sala.salas.service.TiposSalasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tipos-salas")
@Tag(name = "TiposSalas V2", description = "Operaciones HATEOAS sobre relación salas-tipos")
public class TiposSalasControllerV2 {

    @Autowired
    private TiposSalasService tiposSalasService;

    @Autowired
    private TiposSalasModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las relaciones tipos-salas con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<TiposSalasDTO>>> listar() {
        List<EntityModel<TiposSalasDTO>> lista = tiposSalasService.listar()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        CollectionModel<EntityModel<TiposSalasDTO>> collection = CollectionModel.of(lista,
            linkTo(methodOn(TiposSalasControllerV2.class).listar()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar relación por ID con links HATEOAS")
    public ResponseEntity<EntityModel<TiposSalasDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(assembler.toModel(tiposSalasService.buscarPorId(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear relación tipos-salas")
    public ResponseEntity<EntityModel<TiposSalasDTO>> crear(@RequestBody TiposSalas entidad) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(assembler.toModel(tiposSalasService.guardar(entidad)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar relación tipos-salas")
    public ResponseEntity<EntityModel<TiposSalasDTO>> actualizar(
            @PathVariable Integer id, @RequestBody TiposSalas entidad) {
        try {
            return ResponseEntity.ok(assembler.toModel(tiposSalasService.actualizar(id, entidad)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar relación tipos-salas")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tiposSalasService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
