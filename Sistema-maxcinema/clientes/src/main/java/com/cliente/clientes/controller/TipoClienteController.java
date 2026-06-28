package com.cliente.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.cliente.clientes.DTO.TiposDeClientesDTO;
import com.cliente.clientes.model.TipoCliente;
import com.cliente.clientes.service.TipoClienteService;

@RestController
@RequestMapping("/api/v1/TiposDeClientes")
public class TipoClienteController {
    @Autowired
    private TipoClienteService tipoClienteService;

    @GetMapping
    public ResponseEntity<List<TiposDeClientesDTO>> todosLosMetodosDePago() {
        List<TiposDeClientesDTO> metodos = tipoClienteService.obtenerTodos();
        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiposDeClientesDTO> buscarPorId(@PathVariable Integer id) {
        try {
            TiposDeClientesDTO metodo = tipoClienteService.buscarPorId(id);
            return new ResponseEntity<>(metodo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TiposDeClientesDTO> agregarTipoCliente(@RequestBody TipoCliente tipoCliente) {
        try {
            TiposDeClientesDTO guardado = tipoClienteService.agregarTipoCliente(tipoCliente);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiposDeClientesDTO> actualizarTipoCliente(@PathVariable Integer id, @RequestBody TipoCliente tipoCliente) {
        try{
            TiposDeClientesDTO newTipoCliente = tipoClienteService.editarTipoCliente(id, tipoCliente);
            return new ResponseEntity<>(newTipoCliente, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoCliente(@PathVariable Integer id) {
        try {
            tipoClienteService.eliminarTipoCliente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}