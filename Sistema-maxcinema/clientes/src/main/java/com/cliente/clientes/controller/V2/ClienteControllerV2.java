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

import com.cliente.clientes.DTO.ClienteDTO;
import com.cliente.clientes.model.Cliente;
import com.cliente.clientes.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import com.cliente.clientes.assemblers.ClienteModelAssembler;

@RestController
@RequestMapping("/api/v2/clientes")
@Slf4j
@Tag(name = "Clientes V2", description = "Operaciones HATEOAS sobre clientes")
public class ClienteControllerV2 {
    
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assemblerClientes;

    @GetMapping
    @Operation(summary = "Listar todos los clientes con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> todosLosClientes() {
        List<EntityModel<ClienteDTO>> clientes = clienteService.obtenerTodos()
            .stream()
            .map(assemblerClientes::toModel)
            .collect(Collectors.toList());
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CollectionModel<EntityModel<ClienteDTO>> collection = CollectionModel.of(clientes,
            linkTo(methodOn(ClienteControllerV2.class).todosLosClientes()).withSelfRel());
        
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un cliente por ID con links HATEOAS")
    public ResponseEntity<EntityModel<ClienteDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            ClienteDTO cliente = clienteService.buscarPorId(id);
            return ResponseEntity.ok(assemblerClientes.toModel(cliente));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo cliente con links HATEOAS")
    public ResponseEntity<EntityModel<ClienteDTO>> agregarCliente(@RequestBody Cliente cliente) {
        try {
            ClienteDTO guardado = clienteService.agregarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(assemblerClientes.toModel(guardado));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una comuna existente con links HATEOAS")
    public ResponseEntity<EntityModel<ClienteDTO>> actualizarComuna(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            ClienteDTO newCliente = clienteService.editarCliente(id, cliente);
            return ResponseEntity.ok(assemblerClientes.toModel(newCliente));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente por ID")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarClienteParcial(@PathVariable Integer id, @RequestBody Cliente cliente){
        try {
            ClienteDTO newCliente = clienteService.editarCliente(id, cliente);
            return new ResponseEntity<>(newCliente,HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}