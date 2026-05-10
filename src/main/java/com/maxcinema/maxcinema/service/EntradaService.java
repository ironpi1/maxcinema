package com.maxcinema.maxcinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.EntradaDTO;
import com.maxcinema.maxcinema.model.Entrada;
import com.maxcinema.maxcinema.repository.EntradaRepository;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    //public EntradaDTO convertirADTO(Entrada entrada) {
    //    EntradaDTO dto = new EntradaDTO();
    //    dto.setPelicula(entrada.getPelicula().getTitulo());
    //    dto.setHorario(entrada.getHorario().toString());
    //    dto.setSala(entrada.getSala().getNombre());
    //    dto.setClienteRut(entrada.getCliente().getRut());
    //    return dto;
    //}
}
