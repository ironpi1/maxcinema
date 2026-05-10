package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.TipoSala;
import com.maxcinema.maxcinema.repository.TipoSalaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoSalaService {
    @Autowired
    private TipoSalaRepository tipoSalaRepository;

    public  List<TipoSala> ListarTipoSala(){
        return tipoSalaRepository.findAll();
    }

    public TipoSala guardarTipoSala(TipoSala tipoSala){
        return tipoSalaRepository.save(tipoSala);    
    }
    public String eliminarTipoSala(Integer id){
        try {
            TipoSala tipoSala = tipoSalaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id, el id" + id + "no existe"));
            tipoSalaRepository.delete(tipoSala);
            return "el tipo de sala" + tipoSala.getNombre() + "ha sido eliminado exitosamente";       
        } catch (RuntimeException e) {
            return e.getMessage();
        }



    }
}
