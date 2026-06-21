package com.cine.cines.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.cines.DTO.ComunaDTO;
import com.cine.cines.model.Comuna;
import com.cine.cines.repository.ComunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    
    @Autowired
    private ComunaRepository comunaRepository;

    private ComunaDTO convertirADTO(Comuna comuna){
        ComunaDTO dto = new ComunaDTO();
        dto.setComuna_id(comuna.getId());
        dto.setNombre(comuna.getNombre());
        return dto;
    }

    public List<ComunaDTO> listarComuna(){
        return comunaRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    public ComunaDTO guardarComuna(Comuna comuna){
        Comuna guardada = comunaRepository.save(comuna);
        return convertirADTO(guardada);
    }

    public String eliminarComuna(Integer id){
        try {
            Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id " + id + " no existe"));
            comunaRepository.delete(comuna);
            return "la comuna '" + comuna.getNombre() + "' fue eliminada exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public ComunaDTO actualizarComuna(Integer id, Comuna comuna){
        Comuna comuna2 = comunaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + " no existe"));
        if(comuna.getNombre() != null){
            comuna2.setNombre(comuna.getNombre());
        }
        return convertirADTO(comunaRepository.save(comuna2));
    }

    public List<ComunaDTO> buscarComunaPorNombre(String nombre){
        return comunaRepository.findByNombre(nombre).stream()
            .map(this::convertirADTO)
            .toList();
    }
    
    public ComunaDTO buscarComunaPorId(Integer id){
        Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible encontrar la comuna con id " + id));
        return convertirADTO(comuna);
    }
}
