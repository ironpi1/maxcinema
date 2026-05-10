package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.ClienteDTO;
import com.maxcinema.maxcinema.model.Cliente;
import com.maxcinema.maxcinema.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> obtenerTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
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
