package com.sala.salas.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sala.salas.DTO.TipoSalaDTO;
import com.sala.salas.assemblers.TipoSalaModelAssembler;
import com.sala.salas.service.TipoSalaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tipo-sala")
@Tag(name = "Tipos de Sala V2", description = "Operaciones HATEOAS sobre tipos de sala")
public class TipoSalaControllerV2 {

    @Autowired
    private TipoSalaService tipoSalaService;

    @Autowired
    private TipoSalaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los tipos de sala con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<TipoSalaDTO>>> listar() {
        List<EntityModel<TipoSalaDTO>> tipos = tipoSalaService.listar()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (tipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CollectionModel<EntityModel<TipoSalaDTO>> collection = CollectionModel.of(tipos,
            linkTo(methodOn(TipoSalaControllerV2.class).listar()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de sala por ID con links HATEOAS")
    public ResponseEntity<EntityModel<TipoSalaDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            TipoSalaDTO tipoSala = tipoSalaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(tipoSala));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un tipo de sala")
    public ResponseEntity<EntityModel<TipoSalaDTO>> crear(@RequestBody TipoSalaDTO dto) {
        try {
            TipoSalaDTO guardado = tipoSalaService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de sala")
    public ResponseEntity<EntityModel<TipoSalaDTO>> actualizar(
            @PathVariable Integer id, @RequestBody TipoSalaDTO dto) {
        try {
            TipoSalaDTO actualizado = tipoSalaService.actualizar(id, dto);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de sala")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            tipoSalaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
