package com.maxcinema.maxcinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.MetodoPagoDTO;
import com.maxcinema.maxcinema.model.MetodoPago;
import com.maxcinema.maxcinema.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoDTO convertirADTO(MetodoPago metodoPago) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setTipo(metodoPago.getTipoPago());
        return dto;
    }
    
}
