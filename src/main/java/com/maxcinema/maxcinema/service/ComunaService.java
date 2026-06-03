package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.Comuna;
import com.maxcinema.maxcinema.repository.ComunaRepository;
import com.maxcinema.maxcinema.DTO.ComunaDto;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    private ComunaDto convertirADTO(Comuna comuna){
        ComunaDto dto = new ComunaDto();
        dto.setComuna_id(comuna.getId());
        dto.setNombre(comuna.getNombre());
        return dto;
    }

    public List<ComunaDto> listarComuna(){
        return comunaRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    public ComunaDto guardarComuna(Comuna comuna){
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

    public ComunaDto actualizarComuna(Integer id, Comuna comuna){
        Comuna comuna2 = comunaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + " no existe"));
        if(comuna.getNombre() != null){
            comuna2.setNombre(comuna.getNombre());
        }
        return convertirADTO(comunaRepository.save(comuna2));
    }

    public List<ComunaDto> buscarComunaPorNombre(String nombre){
        return comunaRepository.findByNombre(nombre).stream()
            .map(this::convertirADTO)
            .toList();
    }
    
    public ComunaDto buscarComunaPorId(Integer id){
        Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible encontrar la comuna con id " + id));
        return convertirADTO(comuna);
    }
}