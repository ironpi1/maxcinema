package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pelicula.peliculas.DTO.DirectorDTO;
import com.pelicula.peliculas.assemblers.DirectorModelAssembler;
import com.pelicula.peliculas.model.Director;
import com.pelicula.peliculas.service.DirectorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/directores")
@Tag(name = "Directores V2", description = "Operaciones HATEOAS sobre directores")
public class DirectorControllerV2 {

    @Autowired
    private DirectorService directorService;

    @Autowired
    private DirectorModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los directores con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<DirectorDTO>>> listarTodos() {
        List<EntityModel<DirectorDTO>> directores = directorService.obtenerTodos()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (directores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CollectionModel<EntityModel<DirectorDTO>> collection = CollectionModel.of(directores,
            linkTo(methodOn(DirectorControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar director por ID con links HATEOAS")
    public ResponseEntity<EntityModel<DirectorDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            DirectorDTO director = directorService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(director));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un director")
    public ResponseEntity<EntityModel<DirectorDTO>> crear(@RequestBody Director director) {
        try {
            DirectorDTO guardado = directorService.guardarDirector(director);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un director")
    public ResponseEntity<EntityModel<DirectorDTO>> actualizar(
            @PathVariable Integer id, @RequestBody Director director) {
        try {
            DirectorDTO actualizado = directorService.actualizarDirector(id, director);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un director")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            String resultado = directorService.eliminar(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
