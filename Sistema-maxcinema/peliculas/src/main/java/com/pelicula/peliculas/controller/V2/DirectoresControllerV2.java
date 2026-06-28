package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pelicula.peliculas.DTO.DirectoresDTO;
import com.pelicula.peliculas.assemblers.DirectoresModelAssembler;
import com.pelicula.peliculas.model.Directores;
import com.pelicula.peliculas.service.DirectoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/directores-pelicula")
@Tag(name = "Directores-Película V2", description = "Operaciones HATEOAS sobre relación directores-película")
public class DirectoresControllerV2 {

    @Autowired private DirectoresService directoresService;
    @Autowired private DirectoresModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los directores-película")
    public ResponseEntity<CollectionModel<EntityModel<DirectoresDTO>>> listarTodos() {
        List<EntityModel<DirectoresDTO>> lista = directoresService.obtenerTodos()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(DirectoresControllerV2.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<EntityModel<DirectoresDTO>> buscarPorId(@PathVariable Integer id) {
        try { return ResponseEntity.ok(assembler.toModel(directoresService.buscarPorId(id))); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/director/{directorId}")
    @Operation(summary = "Películas por director")
    public ResponseEntity<CollectionModel<EntityModel<DirectoresDTO>>> porDirector(@PathVariable Integer directorId) {
        List<EntityModel<DirectoresDTO>> lista = directoresService.buscarPeliculasPorDirector(directorId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(DirectoresControllerV2.class).porDirector(directorId)).withSelfRel()));
    }

    @GetMapping("/pelicula/{peliculaId}")
    @Operation(summary = "Directores por película")
    public ResponseEntity<CollectionModel<EntityModel<DirectoresDTO>>> porPelicula(@PathVariable Integer peliculaId) {
        List<EntityModel<DirectoresDTO>> lista = directoresService.buscarDirectoresPorPelicula(peliculaId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(DirectoresControllerV2.class).porPelicula(peliculaId)).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<DirectoresDTO>> crear(@RequestBody Directores directores) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(directoresService.guardar(directores))); }
        catch (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DirectoresDTO>> actualizar(@PathVariable Integer id, @RequestBody Directores directores) {
        try { return ResponseEntity.ok(assembler.toModel(directoresService.actualizar(id, directores))); }
        catch (RuntimeException e) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(directoresService.eliminar(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
