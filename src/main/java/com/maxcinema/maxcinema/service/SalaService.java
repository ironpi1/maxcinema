package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.Sala;
import com.maxcinema.maxcinema.repository.SalaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    public List<Sala> Listarsala(){
        return salaRepository.findAll();
    }

    public Sala guardarSala(Sala sala){
        return salaRepository.save(sala);
    }

    public String eliminarSala(Integer id){
        try {
            Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id, el id" + id + "no existe"));
            salaRepository.delete(sala);
            return "la sala" + sala.getNombre() + "fue eliminado exitosamente";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
        
    }

    public Sala actualizarSala(Integer id, Sala Sala){
        Sala sala2 = salaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id" + id + "no existe"));
        if(Sala.getNombre() != null){
            sala2.setNombre(Sala.getNombre());
        }

        if(Sala.getNumeroDeSala() != null){
            sala2.setNumeroDeSala(Sala.getNumeroDeSala());
        }
        return salaRepository.save(sala2);
    }

    public List<Sala> buscarSalaPorNombre(String nombre){
        return salaRepository.findByNombre(nombre);
    }

    public List<Sala> buscarSalaPorNumeroSala(Integer numeroSala){
        return salaRepository.findByNumeroDeSala(numeroSala);
    }
}