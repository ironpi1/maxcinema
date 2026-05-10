package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.cine;
import com.maxcinema.maxcinema.repository.CineRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CineService {
    @Autowired
    private CineRepository cineRepository;

    public List<cine> ListarCine(){
        return cineRepository.findAll();
    }

    public cine guardarCine(cine cine){
        return cineRepository.save(cine);
    }

    public String eliminarCine(Integer id){
        try {
            cine cine = cineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar el cine con id " + id + "no existe"));
            cineRepository.delete(cine);
            return "el cine" + cine.getNombre() + "ha sido retirado exitosamente";
            
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    public cine actualizarCine(Integer id, cine Cine){
        cine cine2 = cineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id" + id + "no existe"));

        if(Cine.getNombre() != null){
            cine2.setNombre(Cine.getNombre());
        }
        if(Cine.getDireccion() != null){
            cine2.setDireccion(Cine.getDireccion());
        }
        return cineRepository.save(cine2);
    }

    public List<cine> buscarCinePorNombre(String nombre){
        return cineRepository.findByNombre(nombre);
    }

    public List<cine> buscarcinePorDireccion(String direccion){
        return cineRepository.findByDireccion(direccion);
    }
}