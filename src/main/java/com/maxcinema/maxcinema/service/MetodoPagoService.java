package com.maxcinema.maxcinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.MetodoPagoDTO;
import com.maxcinema.maxcinema.model.MetodoPago;
import com.maxcinema.maxcinema.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPagoDTO> obtenerTodos() {
        return metodoPagoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MetodoPagoDTO buscarPorId(Integer id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        return convertirADTO(metodoPago);
    }

    public MetodoPago agregarrMetodoPago(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public MetodoPago editarMetodoPago(Integer id, MetodoPago metodoPago) {
        MetodoPago metodo = metodoPagoRepository.findById(id).orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        if (metodoPago.getTipoPago() != null) {
            metodo.setTipoPago(metodoPago.getTipoPago());
        }
        return metodoPagoRepository.save(metodo);
    }

    public String eliminarMetodoPago(Integer id) {
        try {
            MetodoPago metodoPago = metodoPagoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Método de pago no existe"));
            metodoPagoRepository.delete(metodoPago);
            return "Método de pago eliminado correctamente";
        } catch (Exception e) {
            return "No se pudo eliminar el método de pago: " + e.getMessage();
        }
    }

    public MetodoPagoDTO convertirADTO(MetodoPago metodoPago) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setId(metodoPago.getId());
        dto.setNombreTipo(metodoPago.getTipoPago());
        return dto;
    }
    
}
