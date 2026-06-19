package com.cliente.clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cliente.clientes.DTO.EntradasDTO;
import com.cliente.clientes.DTO.PeliculaExternaDTO;
import com.cliente.clientes.model.Entrada;
import com.cliente.clientes.repository.EntradaRepository;

import reactor.core.publisher.Mono;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

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
        if (entrada.getPeliculaId() != null) {
            ticket.setPeliculaId(entrada.getPeliculaId());
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
        dto.setHorario(entrada.getHorario().toString());
        dto.setTipoEntrada(entrada.getTipoEntrada());
        dto.setCliente(entrada.getCliente().getRut());

        PeliculaExternaDTO pelicula = obtenerPelicula(entrada.getPeliculaId());
        dto.setPelicula(pelicula != null ? pelicula.getTitulo() : "Pelicula no disponible");
       return dto;
    }
    private PeliculaExternaDTO obtenerPelicula(Integer peliculaId) {
        try {
            return webClientBuilder.build()
                .get()
                .uri("http://peliculas/api/v1/peliculas/" + peliculaId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(PeliculaExternaDTO.class)
                .block();
        } catch (Exception e) {
            PeliculaExternaDTO peliculaRecuperada = new PeliculaExternaDTO();
            peliculaRecuperada.setId(0);
            peliculaRecuperada.setTitulo("No se pudo conectar con el microservicio de peliculas");
            return peliculaRecuperada;
        }
    }
}