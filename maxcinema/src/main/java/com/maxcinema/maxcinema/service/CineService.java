package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.Cine;
import com.maxcinema.maxcinema.repository.CineRepository;
import com.maxcinema.maxcinema.DTO.CineDto;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CineService {
    @Autowired
    private CineRepository cineRepository;

    private CineDto convertirADTO(Cine cine){
        CineDto dto = new CineDto();
        dto.setId(cine.getId());
        dto.setNombre(cine.getNombre());
        dto.setDireccion(cine.getDireccion());
        if (cine.getComuna() != null) {
            dto.setNombreComuna(cine.getComuna().getNombre());
        }
        return dto;
    }

    public List<CineDto> listarCine(){
        return cineRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    public CineDto guardarCine(Cine cine){
        Cine guardado = cineRepository.save(cine);
        return convertirADTO(guardado);
    }

    public String eliminarCine(Integer id){
        try {
            Cine cine = cineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar el cine con id " + id + "no existe"));
            cineRepository.delete(cine);
            return "el cine" + cine.getNombre() + "ha sido retirado exitosamente";
            
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    public CineDto actualizarCine(Integer id, Cine cine){
        Cine cine2 = cineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id" + id + "no existe"));

        if(cine.getNombre() != null){
            cine2.setNombre(cine.getNombre());
        }
        if(cine.getDireccion() != null){
            cine2.setDireccion(cine.getDireccion());
        }
        return convertirADTO(cineRepository.save(cine2));
    }

    public List<CineDto> buscarCinePorNombre(String nombre){
        return cineRepository.findByNombre(nombre).stream()
            .map(this::convertirADTO)
            .toList();
    }

    public List<CineDto> buscarcinePorDireccion(String direccion){
        return cineRepository.findByDireccion(direccion).stream()
            .map(this::convertirADTO)
            .toList();
    }
    
    public CineDto buscarCinePorId(Integer id){
        Cine cine = cineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible encontrar el cine con id " + id));
        return convertirADTO(cine);
    }
    
}