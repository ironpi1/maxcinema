package com.cine.cines.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.cines.DTO.ComunaDTO;
import com.cine.cines.assemblers.ComunaModelAssembler;
import com.cine.cines.model.Comuna;
import com.cine.cines.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/v2/comunas")
@Tag(name = "Comunas V2", description = "Operaciones HATEOAS sobre comunas")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las comunas con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<ComunaDTO>>> listarComunas() {
        List<EntityModel<ComunaDTO>> comunas = comunaService.listarComuna()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (comunas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CollectionModel<EntityModel<ComunaDTO>> collection = CollectionModel.of(comunas,
            linkTo(methodOn(ComunaControllerV2.class).listarComunas()).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comuna por ID con links HATEOAS")
    public ResponseEntity<EntityModel<ComunaDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            ComunaDTO comuna = comunaService.buscarComunaPorId(id);
            return ResponseEntity.ok(assembler.toModel(comuna));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva comuna con links HATEOAS")
    public ResponseEntity<EntityModel<ComunaDTO>> guardarComuna(@RequestBody Comuna comuna) {
        try {
            ComunaDTO guardado = comunaService.guardarComuna(comuna);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar una comuna existente con links HATEOAS")
    public ResponseEntity<EntityModel<ComunaDTO>> editarComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            ComunaDTO editado = comunaService.actualizarComuna(id, comuna);
            return ResponseEntity.ok(assembler.toModel(editado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una comuna existente con links HATEOAS")
    public ResponseEntity<EntityModel<ComunaDTO>> actualizarComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            ComunaDTO newComuna = comunaService.actualizarComuna(id, comuna);
            return ResponseEntity.ok(assembler.toModel(newComuna));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
