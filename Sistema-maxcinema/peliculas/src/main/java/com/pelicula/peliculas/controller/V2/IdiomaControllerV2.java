package com.pelicula.peliculas.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pelicula.peliculas.DTO.IdiomaDTO;
import com.pelicula.peliculas.assemblers.IdiomaModelAssembler;
import com.pelicula.peliculas.model.Idioma;
import com.pelicula.peliculas.service.IdiomaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/idiomas")
@Tag(name = "Idiomas V2", description = "Operaciones HATEOAS sobre idiomas")
public class IdiomaControllerV2 {

    @Autowired
    private IdiomaService idiomaService;

    @Autowired
    private IdiomaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los idiomas con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<IdiomaDTO>>> listarTodos() {
        List<EntityModel<IdiomaDTO>> idiomas = idiomaService.obtenerTodos()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (idiomas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CollectionModel<EntityModel<IdiomaDTO>> collection = CollectionModel.of(idiomas,
            linkTo(methodOn(IdiomaControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar idioma por ID con links HATEOAS")
    public ResponseEntity<EntityModel<IdiomaDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            IdiomaDTO idioma = idiomaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(idioma));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un idioma")
    public ResponseEntity<EntityModel<IdiomaDTO>> crear(@RequestBody Idioma idioma) {
        try {
            IdiomaDTO guardado = idiomaService.guardar(idioma);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un idioma")
    public ResponseEntity<EntityModel<IdiomaDTO>> actualizar(
            @PathVariable Integer id, @RequestBody Idioma idioma) {
        try {
            IdiomaDTO actualizado = idiomaService.actualizar(id, idioma);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un idioma")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            String resultado = idiomaService.eliminar(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
