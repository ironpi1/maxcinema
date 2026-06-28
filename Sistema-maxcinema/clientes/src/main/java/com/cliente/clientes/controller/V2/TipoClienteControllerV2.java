package com.cliente.clientes.controller.V2;

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
import com.cliente.clientes.assemblers.TipoClienteAssembler;

@RestController
@RequestMapping("/api/v2/TiposDeClientes")
public class TipoClienteControllerV2 {
    @Autowired
    private TipoClienteService tipoClienteService;

    @Autowired
    private TipoClienteAssembler tipoClienteAssembler;

    @GetMapping
    public ResponseEntity<List<TiposDeClientesDTO>> TodosLosClientes() {
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
    public ResponseEntity<TipoCliente> agregarTipoCliente(@RequestBody TipoCliente tipoCliente) {
        try {
            TipoCliente guardado = tipoClienteService.agregarTipoCliente(tipoCliente);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCliente> actualizarTipoCliente(@PathVariable Integer id, @RequestBody TipoCliente tipoCliente) {
        try{
            TipoCliente newTipoCliente = tipoClienteService.editarTipoCliente(id, tipoCliente);
            return new ResponseEntity<>(newTipoCliente, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipoCliente(@PathVariable Integer id) {
        String resultado = tipoClienteService.eliminarTipoCliente(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}