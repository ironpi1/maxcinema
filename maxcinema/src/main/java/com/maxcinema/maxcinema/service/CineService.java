package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.Cine;
import com.maxcinema.maxcinema.repository.CineRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CineService {
    @Autowired
    private CineRepository cineRepository;

    public List<Cine> ListarCine(){
        return cineRepository.findAll();
    }

    public Cine guardarCine(Cine cine){
        return cineRepository.save(cine);
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
    public Cine actualizarCine(Integer id, Cine cine){
        Cine cine2 = cineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id" + id + "no existe"));

        if(cine.getNombre() != null){
            cine2.setNombre(cine.getNombre());
        }
        if(cine.getDireccion() != null){
            cine2.setDireccion(cine.getDireccion());
        }
        return cineRepository.save(cine2);
    }

    public List<Cine> buscarCinePorNombre(String nombre){
        return cineRepository.findByNombre(nombre);
    }

    public List<Cine> buscarcinePorDireccion(String direccion){
        return cineRepository.findByDireccion(direccion);
    }
    
}