package com.maxcinema.maxcinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.TipoAsientoDTO;
import com.maxcinema.maxcinema.model.TipoAsiento;
import com.maxcinema.maxcinema.repository.TipoAsientoRepository;

@Service
public class TipoAsientoService {
    @Autowired
    private TipoAsientoRepository tipoAsientoRepository;
    public TipoAsientoDTO convertirADTO(TipoAsiento tipoAsiento) {
        TipoAsientoDTO dto = new TipoAsientoDTO();
        dto.setNombre(tipoAsiento.getNombre());
        return dto;
    }
}
