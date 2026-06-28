package com.cliente.clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cliente.clientes.DTO.ClienteDTO;
import com.cliente.clientes.model.Cliente;
import com.cliente.clientes.repository.ClienteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> obtenerTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ClienteDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirADTO(cliente);
    }

    public ClienteDTO agregarCliente(Cliente cliente) {
        return convertirADTO(clienteRepository.save(cliente));
    }

    public ClienteDTO editarCliente(Integer id, Cliente cliente) {
        log.info("Editando cliente con ID: {}", id);
        Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No se encontró cliente con ID: {}", id);
                return new RuntimeException("Cliente no encontrado");
            });
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setRut(cliente.getRut());
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        log.info("Cliente actualizado exitosamente con ID: {}", clienteActualizado.getId());
        return convertirADTO(clienteActualizado);
    }

    public String eliminarCliente(Integer id) {
        try {
            Cliente client = clienteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no existe"));
            clienteRepository.delete(client);
            return "Cliente eliminado correctamente";
        }catch (Exception e) {
            return e.getMessage();
        } 
    }
    
    public ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre(cliente.getNombre());
        dto.setRut(cliente.getRut());
        
        if (cliente.getRut() != null) {
            dto.setRut(cliente.getRut());
        }else {
            dto.setRut("No registrado");
        }
        return dto;
    }
}
