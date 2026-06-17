package com.cliente.clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.clientes.DTO.EntradasDTO;
import com.cliente.clientes.model.Entrada;
import com.cliente.clientes.repository.EntradaRepository;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    public List<EntradasDTO> obtenerTodos() {
        return entradaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EntradasDTO buscarPorId(Integer id) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada"));
        return convertirADTO(entrada);
    }

    public Entrada crearEntrada(Entrada entrada) {
        return entradaRepository.save(entrada);
    }

    public Entrada editarEntrada(Integer id, Entrada entrada) {
        Entrada ticket = entradaRepository.findById(id).orElseThrow(() -> new RuntimeException("Entrada no existe"));
        if (entrada.getPelicula() != null) {
            ticket.setPelicula(entrada.getPelicula());
        }
        if (entrada.getCliente() != null) {
            ticket.setCliente(entrada.getCliente());
        }
        if (entrada.getTipoEntrada() != null) {
            ticket.setTipoEntrada(entrada.getTipoEntrada());
        }
        if (entrada.getPrecio() != null) {
            ticket.setPrecio(entrada.getPrecio());
        }
        if (entrada.getCantidad() != null) {
            ticket.setCantidad(entrada.getCantidad());
        }
        if (entrada.getHorario() != null) {
            ticket.setHorario(entrada.getHorario());
        }
        if (entrada.getMetodoPago() != null) {
            ticket.setMetodoPago(entrada.getMetodoPago());
        }
        return entradaRepository.save(ticket);
    }

    public String cancelarEntrada(Integer id) {
        try {
            Entrada entrada = entradaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Entrada no existe"));
            entradaRepository.delete(entrada);
            return "Entrada cancelada exitosamente";
        } catch (Exception e) {
            return "No se pudo cancelar la entrada: " + e.getMessage();
        }
    }

    public EntradasDTO convertirADTO(Entrada entrada) {
        EntradasDTO dto = new EntradasDTO();
        dto.setId(entrada.getId());
        dto.setPelicula(entrada.getPelicula().getTitulo());
        dto.setHorario(entrada.getHorario().toString());
        dto.setTipoEntrada(entrada.getTipoEntrada());
        dto.setCliente(entrada.getCliente().getRut());
       return dto;
    }
}