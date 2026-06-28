package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pelicula.peliculas.DTO.SalasPeliculaDTO;
import com.pelicula.peliculas.assemblers.SalasPeliculaModelAssembler;
import com.pelicula.peliculas.model.SalasPelicula;
import com.pelicula.peliculas.service.SalasPeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/salas-pelicula")
@Tag(name = "Salas-Película V2", description = "Operaciones HATEOAS sobre relación salas-película")
public class SalasPeliculaControllerV2 {

    @Autowired private SalasPeliculaService salasPeliculaService;
    @Autowired private SalasPeliculaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SalasPeliculaDTO>>> listarTodos() {
        List<EntityModel<SalasPeliculaDTO>> lista = salasPeliculaService.obtenerTodos()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(SalasPeliculaControllerV2.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SalasPeliculaDTO>> buscarPorId(@PathVariable Integer id) {
        try { return ResponseEntity.ok(assembler.toModel(salasPeliculaService.buscarPorId(id))); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/pelicula/{peliculaId}")
    @Operation(summary = "Salas de una película")
    public ResponseEntity<CollectionModel<EntityModel<SalasPeliculaDTO>>> porPelicula(@PathVariable Integer peliculaId) {
        List<EntityModel<SalasPeliculaDTO>> lista = salasPeliculaService.buscarPorPelicula(peliculaId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(SalasPeliculaControllerV2.class).porPelicula(peliculaId)).withSelfRel()));
    }

    @GetMapping("/sala/{salaId}")
    @Operation(summary = "Películas de una sala")
    public ResponseEntity<CollectionModel<EntityModel<SalasPeliculaDTO>>> porSala(@PathVariable Integer salaId) {
        List<EntityModel<SalasPeliculaDTO>> lista = salasPeliculaService.buscarPorSala(salaId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(SalasPeliculaControllerV2.class).porSala(salaId)).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<SalasPeliculaDTO>> crear(@RequestBody SalasPelicula sp) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(salasPeliculaService.guardar(sp))); }
        catch (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SalasPeliculaDTO>> actualizar(@PathVariable Integer id, @RequestBody SalasPelicula sp) {
        try { return ResponseEntity.ok(assembler.toModel(salasPeliculaService.actualizar(id, sp))); }
        catch (RuntimeException e) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(salasPeliculaService.eliminar(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
