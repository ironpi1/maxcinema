package com.cliente.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.cliente.clientes.DTO.MetodosDePagoDTO;
import com.cliente.clientes.model.MetodoPago;
import com.cliente.clientes.service.MetodoPagoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/metodos-pago")
@Slf4j
public class MetodoPagoController {
    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodosDePagoDTO>> todosLosMetodosDePago() {
        List<MetodosDePagoDTO> metodos = metodoPagoService.obtenerTodos();
        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodosDePagoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            MetodosDePagoDTO metodo = metodoPagoService.buscarPorId(id);
            return new ResponseEntity<>(metodo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MetodoPago> agregarMetodoDePago(@RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago guardado = metodoPagoService.agregarrMetodoPago(metodoPago);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodosDePagoDTO> actualizarMetodoDePago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        log.debug("PUT /metodos-pago/{}",id);
        return ResponseEntity.ok(metodoPagoService.editarMetodoPago(id, metodoPago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarMetodoDePago(@PathVariable Integer id) {
        String resultado = metodoPagoService.eliminarMetodoPago(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<MetodosDePagoDTO> actualizarMetodopagoParcial(@PathVariable Integer id, @RequestBody MetodoPago metodoPago){
        try {
            MetodosDePagoDTO newMetodo = metodoPagoService.editarMetodoPago(id, metodoPago);
            return new ResponseEntity<>(newMetodo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}