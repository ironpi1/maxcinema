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

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
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

    public EntradasDTO editarEntrada(Integer id, Entrada entradan) {
        log.info("Editando entrada con ID: {}", id);
        Entrada existingEntrada = entradaRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No se encontró entrada con ID: {}", id);
                return new RuntimeException("Entrada no encontrada");
            });
        existingEntrada.setTipoEntrada(entradan.getTipoEntrada());
        existingEntrada.setPrecio(entradan.getPrecio());
        existingEntrada.setCantidad(entradan.getCantidad());
        existingEntrada.setHorario(entradan.getHorario());
        Entrada guardada = entradaRepository.save(existingEntrada);
        log.info("Entrada actualizada exitosamente con ID: {}", guardada.getId());
        return convertirADTO(guardada);
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

    public EntradasDTO convertirADTO(Entrada guardado) {
        EntradasDTO dto = new EntradasDTO();
        dto.setId(guardado.getId());
        dto.setHorario(guardado.getHorario().toString());
        dto.setTipoEntrada(guardado.getTipoEntrada());
        dto.setCliente(guardado.getCliente().getRut());

        PeliculaExternaDTO pelicula = obtenerPelicula(guardado.getPeliculaId());
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