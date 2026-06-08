package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.EntradaDTO;
import com.maxcinema.maxcinema.model.Entrada;
import com.maxcinema.maxcinema.repository.EntradaRepository;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    public List<EntradaDTO> obtenerTodos() {
        return entradaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EntradaDTO buscarPorId(Integer id) {
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

    public EntradaDTO convertirADTO(Entrada entrada) {
        EntradaDTO dto = new EntradaDTO();
        dto.setId(entrada.getId());
        dto.setPelicula(entrada.getPelicula().getTitulo());
        dto.setHorario(entrada.getHorario().toString());
        dto.setTipoEntrada(entrada.getTipoEntrada());
        dto.setCliente(entrada.getCliente().getRut());
       return dto;
    }
}
