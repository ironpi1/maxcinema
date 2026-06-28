package com.cliente.clientes.controller.V2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.clientes.DTO.EntradasDTO;
import com.cliente.clientes.model.Entrada;
import com.cliente.clientes.service.EntradaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import com.cliente.clientes.assemblers.EntradasModelAssembler;

@RestController
@RequestMapping("/api/v2/entradas")
@Slf4j
@Tag(name = "Entradas V2", description = "Operaciones HATEOAS sobre entradas")
public class EntradaControllerV2 {
    
    @Autowired
    private EntradaService entradaService;

    @Autowired
    private EntradasModelAssembler assemblerEntradas;

    @GetMapping
    @Operation(summary = "Listar todas las entradas con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<EntradasDTO>>> todosLasEntradas() {
        List<EntityModel<EntradasDTO>> entradas = entradaService.obtenerTodos()
            .stream()
            .map(assemblerEntradas::toModel)
            .collect(Collectors.toList());
        
            if (entradas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        CollectionModel<EntityModel<EntradasDTO>> collection = CollectionModel.of(entradas,
            linkTo(methodOn(EntradaControllerV2.class).todosLasEntradas()).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar entrada por ID con links HATEOAS")
    public ResponseEntity<EntityModel<EntradasDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            EntradasDTO entrada = entradaService.buscarPorId(id);
            return ResponseEntity.ok(assemblerEntradas.toModel(entrada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva entrada con links HATEOAS")
    public ResponseEntity<EntityModel<EntradasDTO>> agregarEntrada(@RequestBody Entrada entrada) {
        try {
            Entrada guardado = entradaService.crearEntrada(entrada);
            return ResponseEntity.status(HttpStatus.CREATED).body(assemblerEntradas.toModel(entradaService.convertirADTO(guardado)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una entrada existente con links HATEOAS")
    public ResponseEntity<EntityModel<EntradasDTO>> actualizarEntrada(@PathVariable Integer id, @RequestBody Entrada entrada) {
        try{
            EntradasDTO newEntrada = entradaService.editarEntrada(id, entrada);
            return ResponseEntity.ok(assemblerEntradas.toModel(newEntrada));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar una entrada existente")
    public ResponseEntity<Void> cancelarEntrada(@PathVariable Integer id) {
        try {
            entradaService.cancelarEntrada(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
