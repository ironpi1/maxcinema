package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.TipoClienteDTO;
import com.maxcinema.maxcinema.model.TipoCliente;
import com.maxcinema.maxcinema.repository.TipoClienteRepository;

@Service
public class TipoClienteService {

    @Autowired
    private TipoClienteRepository tipoClienteRepository;
    
    public List<TipoClienteDTO> obtenerTodos() {
        return tipoClienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoClienteDTO buscarPorId(Integer id) {
        TipoCliente tipoCliente = tipoClienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de cliente no encontrado"));
        return convertirADTO(tipoCliente);
    }

    public TipoCliente agregarTipoCliente(TipoCliente tipoCliente) {
        return tipoClienteRepository.save(tipoCliente);
    }

    public TipoCliente editarTipoCliente(Integer id, TipoCliente tipoCliente) {
        TipoCliente tipo = tipoClienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de cliente no encontrado"));
        if (tipo.getTipo() != null) {
            tipo.setTipo(tipoCliente.getTipo());
        }
        return tipoClienteRepository.save(tipo);
    }

    public String eliminarTipoCliente(Integer id) {
        try {
            TipoCliente tipoCliente = tipoClienteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tipo de cliente no existe"));
            tipoClienteRepository.delete(tipoCliente);
            return "Tipo de cliente eliminado correctamente";
        } catch (Exception e) {
            return "No se pudo eliminar el tipo de cliente: " + e.getMessage();
        }
    }
    
    public String aplicarDescuento(TipoCliente tipoCliente, Float descuento) {
        descuento = 0.0f;
        if (tipoCliente.getTipo().equals("VIP")) {
            descuento = 0.20f; 
            return "Descuento aplicado: " + (descuento * 100) + "%";
        }
        if (tipoCliente.getTipo().equals("Wom")) {
            descuento = 0.10f;
            return "Descuento aplicado: " + (descuento * 100) + "%";
        }
        if (tipoCliente.getTipo().equals("Estudiante")) {
            descuento = 0.15f;
            return "Descuento aplicado: " + (descuento * 100) + "%";
        } else {
            return "Este tipo de cliente no tiene descuento";
        }
    }

    public TipoClienteDTO convertirADTO(TipoCliente tipoCliente) {
        TipoClienteDTO dto = new TipoClienteDTO();
        dto.setId(tipoCliente.getId());
        dto.setTipo(tipoCliente.getTipo());
        dto.setDescuento(tipoCliente.getDescuento());
        return dto;
    }
}
