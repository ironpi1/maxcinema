package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pelicula.peliculas.DTO.GenerosDTO;
import com.pelicula.peliculas.assemblers.GenerosModelAssembler;
import com.pelicula.peliculas.model.Generos;
import com.pelicula.peliculas.service.GenerosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/generos-pelicula")
@Tag(name = "Géneros-Película V2", description = "Operaciones HATEOAS sobre relación géneros-película")
public class GenerosControllerV2 {

    @Autowired private GenerosService generosService;
    @Autowired private GenerosModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<GenerosDTO>>> listarTodos() {
        List<EntityModel<GenerosDTO>> lista = generosService.obtenerTodos()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(GenerosControllerV2.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GenerosDTO>> buscarPorId(@PathVariable Integer id) {
        try { return ResponseEntity.ok(assembler.toModel(generosService.buscarPorId(id))); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/pelicula/{peliculaId}")
    @Operation(summary = "Géneros de una película")
    public ResponseEntity<CollectionModel<EntityModel<GenerosDTO>>> porPelicula(@PathVariable Integer peliculaId) {
        List<EntityModel<GenerosDTO>> lista = generosService.buscarPorPelicula(peliculaId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(GenerosControllerV2.class).porPelicula(peliculaId)).withSelfRel()));
    }

    @GetMapping("/genero/{generoId}")
    @Operation(summary = "Películas de un género")
    public ResponseEntity<CollectionModel<EntityModel<GenerosDTO>>> porGenero(@PathVariable Integer generoId) {
        List<EntityModel<GenerosDTO>> lista = generosService.buscarPorGenero(generoId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(GenerosControllerV2.class).porGenero(generoId)).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<GenerosDTO>> crear(@RequestBody Generos generos) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(generosService.guardar(generos))); }
        catch (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<GenerosDTO>> actualizar(@PathVariable Integer id, @RequestBody Generos generos) {
        try { return ResponseEntity.ok(assembler.toModel(generosService.actualizar(id, generos))); }
        catch (RuntimeException e) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(generosService.eliminar(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
