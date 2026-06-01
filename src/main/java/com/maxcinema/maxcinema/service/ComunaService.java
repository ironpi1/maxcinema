package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.Comuna;
import com.maxcinema.maxcinema.repository.ComunaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> ListarComuna(){
        return comunaRepository.findAll();
    }

    public Comuna guardarComuna(Comuna comuna){
        return comunaRepository.save(comuna);
    }

    public String eliminarComuna(Integer id){
        try {

            Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id" + id + "no existe"));
            comunaRepository.delete(comuna);
            return "la comuna " + comuna.getNombre() + "fue eliminado exitosamente";
            
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Comuna actualizarComuna(Integer id, Comuna comuna){
        Comuna comuna2 = comunaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + "no existe"));
        if(comuna.getNombre() != null){
            comuna2.setNombre(comuna.getNombre());
        }
        
        return comunaRepository.save(comuna2);
    }

    public List<Comuna> buscarComunaPorNombre(String nombre){
        return comunaRepository.findByNombre(nombre);

    }
}