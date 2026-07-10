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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.clientes.DTO.TiposDeClientesDTO;
import com.cliente.clientes.model.TipoCliente;
import com.cliente.clientes.service.TipoClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.cliente.clientes.assemblers.TipoClienteAssembler;

@RestController
@RequestMapping("/api/v2/TiposDeClientes")
@Tag(name = "Tipos de Clientes V2", description = "Operaciones HATEOAS sobre tipos de clientes")
public class TipoClienteControllerV2 {
    
    @Autowired
    private TipoClienteService tipoClienteService;

    @Autowired
    private TipoClienteAssembler tipoClienteAssembler;

    @GetMapping
    @Operation(summary = "Listar todos los tipos de clientes")
    public ResponseEntity<CollectionModel<EntityModel<TiposDeClientesDTO>>> TodosLosClientes() {
        List<EntityModel<TiposDeClientesDTO>> metodos = tipoClienteService.obtenerTodos()
            .stream()
            .map(tipoClienteAssembler::toModel)
            .collect(Collectors.toList());

        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CollectionModel<EntityModel<TiposDeClientesDTO>> collection = CollectionModel.of(metodos,
            linkTo(methodOn(TipoClienteControllerV2.class).TodosLosClientes()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de cliente por ID")
    public ResponseEntity<EntityModel<TiposDeClientesDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            TiposDeClientesDTO metodo = tipoClienteService.buscarPorId(id);
            return ResponseEntity.ok(tipoClienteAssembler.toModel(metodo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo tipo de cliente")
    public ResponseEntity<EntityModel<TiposDeClientesDTO>> agregarTipoCliente(@RequestBody TipoCliente tipoCliente) {
        try {
            TiposDeClientesDTO guardado = tipoClienteService.agregarTipoCliente(tipoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoClienteAssembler.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de cliente existente")
    public ResponseEntity<EntityModel<TiposDeClientesDTO>> actualizarTipoCliente(@PathVariable Integer id, @RequestBody TipoCliente tipoCliente) {
        try{
            TiposDeClientesDTO newTipoCliente = tipoClienteService.editarTipoCliente(id, tipoCliente);
            return ResponseEntity.ok(tipoClienteAssembler.toModel(newTipoCliente));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de cliente")
    public ResponseEntity<Void> eliminarTipoCliente(@PathVariable Integer id) {
        try {
            tipoClienteService.eliminarTipoCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/id")
    public ResponseEntity<TiposDeClientesDTO> actualizarTipoClientParcial(@PathVariable Integer id, @RequestBody TipoCliente tipoCliente){
        try {
            TiposDeClientesDTO newTiposDeClientes = tipoClienteService.editarTipoCliente(id, tipoCliente);
            return new ResponseEntity<>(newTiposDeClientes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}