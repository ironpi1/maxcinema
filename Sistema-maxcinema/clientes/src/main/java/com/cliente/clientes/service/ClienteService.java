package com.cliente.clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cliente.clientes.DTO.ClienteDTO;
import com.cliente.clientes.model.Cliente;
import com.cliente.clientes.repository.ClienteRepository;

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

    public Cliente agregarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente editarCliente(Integer id, Cliente cliente) {
        Cliente client = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        if (cliente.getNombre() != null) {
            client.setNombre(cliente.getNombre());
        }
        if (cliente.getRut() != null) {
            client.setRut(cliente.getRut());
        }
        if (cliente.getEmail() != null) {
            client.setEmail(cliente.getEmail());
        }
        if (cliente.getTelefono() != null) {
            client.setTelefono(cliente.getTelefono());
        }
        return clienteRepository.save(client);
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
