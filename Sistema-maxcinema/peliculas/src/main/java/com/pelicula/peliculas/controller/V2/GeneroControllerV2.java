package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pelicula.peliculas.DTO.GeneroDTO;
import com.pelicula.peliculas.assemblers.GeneroModelAssembler;
import com.pelicula.peliculas.model.Genero;
import com.pelicula.peliculas.service.GeneroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/generos")
@Tag(name = "Géneros V2", description = "Operaciones HATEOAS sobre géneros")
public class GeneroControllerV2 {

    @Autowired
    private GeneroService generoService;

    @Autowired
    private GeneroModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los géneros con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<GeneroDTO>>> listarTodos() {
        List<EntityModel<GeneroDTO>> generos = generoService.obtenerTodos()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (generos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CollectionModel<EntityModel<GeneroDTO>> collection = CollectionModel.of(generos,
            linkTo(methodOn(GeneroControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar género por ID con links HATEOAS")
    public ResponseEntity<EntityModel<GeneroDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            GeneroDTO genero = generoService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(genero));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un género")
    public ResponseEntity<EntityModel<GeneroDTO>> crear(@RequestBody Genero genero) {
        try {
            GeneroDTO guardado = generoService.guardar(genero);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un género")
    public ResponseEntity<EntityModel<GeneroDTO>> actualizar(
            @PathVariable Integer id, @RequestBody Genero genero) {
        try {
            GeneroDTO actualizado = generoService.actualizar(id, genero);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un género")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            String resultado = generoService.eliminar(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
