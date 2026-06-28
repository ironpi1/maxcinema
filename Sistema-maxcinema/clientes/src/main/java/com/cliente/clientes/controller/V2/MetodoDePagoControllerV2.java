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

import com.cliente.clientes.DTO.MetodosDePagoDTO;
import com.cliente.clientes.model.MetodoPago;
import com.cliente.clientes.service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.cliente.clientes.assemblers.MetodoDePagoAssembler;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v2/metodosPago")
@Slf4j
@Tag(name = "Métodos de Pago V2", description = "Operaciones HATEOAS sobre métodos de pago")
public class MetodoDePagoControllerV2 {
    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private MetodoDePagoAssembler metodoDePagoAssembler;

    @GetMapping
    @Operation(summary = "Listar todos los métodos de pago con links HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<MetodosDePagoDTO>>> todosLosMetodosDePago() {
        List<EntityModel<MetodosDePagoDTO>> metodos = metodoPagoService.obtenerTodos()
            .stream()
            .map(metodoDePagoAssembler::toModel)
            .collect(Collectors.toList());

        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CollectionModel<EntityModel<MetodosDePagoDTO>> collection = CollectionModel.of(metodos,
            linkTo(methodOn(MetodoDePagoControllerV2.class).todosLosMetodosDePago()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar método de pago por ID con links HATEOAS")
    public ResponseEntity<EntityModel<MetodosDePagoDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            MetodosDePagoDTO metodo = metodoPagoService.buscarPorId(id);
            return ResponseEntity.ok(metodoDePagoAssembler.toModel(metodo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo método de pago")
    public ResponseEntity<EntityModel<MetodosDePagoDTO>> agregarMetodoDePago(@RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago guardado = metodoPagoService.agregarrMetodoPago(metodoPago);
            return ResponseEntity.status(HttpStatus.CREATED).body(metodoDePagoAssembler.toModel(metodoPagoService.convertirADTO(guardado)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un método de pago existente")
    public ResponseEntity<EntityModel<MetodosDePagoDTO>> actualizarMetodoDePago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        try{
            MetodosDePagoDTO newMetodoPago = metodoPagoService.editarMetodoPago(id, metodoPago);
            return ResponseEntity.ok(metodoDePagoAssembler.toModel(newMetodoPago));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un método de pago")
    public ResponseEntity<Void> cancelarMetodoDePago(@PathVariable Integer id) {
        try {
            metodoPagoService.eliminarMetodoPago(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}