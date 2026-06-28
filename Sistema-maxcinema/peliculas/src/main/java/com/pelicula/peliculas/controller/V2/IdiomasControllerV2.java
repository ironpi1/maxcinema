package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pelicula.peliculas.DTO.IdiomasDTO;
import com.pelicula.peliculas.assemblers.IdiomasModelAssembler;
import com.pelicula.peliculas.model.Idiomas;
import com.pelicula.peliculas.service.IdiomasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/idiomas-pelicula")
@Tag(name = "Idiomas-Película V2", description = "Operaciones HATEOAS sobre relación idiomas-película")
public class IdiomasControllerV2 {

    @Autowired private IdiomasService idiomasService;
    @Autowired private IdiomasModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<IdiomasDTO>>> listarTodos() {
        List<EntityModel<IdiomasDTO>> lista = idiomasService.obtenerTodos()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(IdiomasControllerV2.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<IdiomasDTO>> buscarPorId(@PathVariable Integer id) {
        try { return ResponseEntity.ok(assembler.toModel(idiomasService.buscarPorId(id))); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/pelicula/{peliculaId}")
    @Operation(summary = "Idiomas de una película")
    public ResponseEntity<CollectionModel<EntityModel<IdiomasDTO>>> porPelicula(@PathVariable Integer peliculaId) {
        List<EntityModel<IdiomasDTO>> lista = idiomasService.buscarPorPelicula(peliculaId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(IdiomasControllerV2.class).porPelicula(peliculaId)).withSelfRel()));
    }

    @GetMapping("/idioma/{idiomaId}")
    @Operation(summary = "Películas de un idioma")
    public ResponseEntity<CollectionModel<EntityModel<IdiomasDTO>>> porIdioma(@PathVariable Integer idiomaId) {
        List<EntityModel<IdiomasDTO>> lista = idiomasService.buscarPorIdioma(idiomaId)
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(CollectionModel.of(lista,
            linkTo(methodOn(IdiomasControllerV2.class).porIdioma(idiomaId)).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<IdiomasDTO>> crear(@RequestBody Idiomas idiomas) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(idiomasService.guardar(idiomas))); }
        catch (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<IdiomasDTO>> actualizar(@PathVariable Integer id, @RequestBody Idiomas idiomas) {
        try { return ResponseEntity.ok(assembler.toModel(idiomasService.actualizar(id, idiomas))); }
        catch (RuntimeException e) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(idiomasService.eliminar(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
