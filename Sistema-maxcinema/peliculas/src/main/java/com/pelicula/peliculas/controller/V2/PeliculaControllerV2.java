package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pelicula.peliculas.DTO.PeliculaDTO;
import com.pelicula.peliculas.assemblers.PeliculaModelAssembler;
import com.pelicula.peliculas.model.Pelicula;
import com.pelicula.peliculas.service.PeliculaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/peliculas")
@Tag(name = "Películas V2", description = "Operaciones HATEOAS sobre películas")
public class PeliculaControllerV2 {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private PeliculaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las películas con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<PeliculaDTO>>> listarTodas() {
        List<EntityModel<PeliculaDTO>> peliculas = peliculaService.obtenerTodas()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (peliculas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CollectionModel<EntityModel<PeliculaDTO>> collection = CollectionModel.of(peliculas,
            linkTo(methodOn(PeliculaControllerV2.class).listarTodas()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar película por ID con links HATEOAS")
    public ResponseEntity<EntityModel<PeliculaDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            PeliculaDTO pelicula = peliculaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(pelicula));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una película")
    public ResponseEntity<EntityModel<PeliculaDTO>> crear(@RequestBody Pelicula pelicula) {
        try {
            PeliculaDTO guardada = peliculaService.guardarPelicula(pelicula);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardada));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una película")
    public ResponseEntity<EntityModel<PeliculaDTO>> actualizar(
            @PathVariable Integer id, @RequestBody Pelicula pelicula) {
        try {
            PeliculaDTO actualizada = peliculaService.actualizarPelicula(id, pelicula);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una película")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            peliculaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
