package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.TiposSalas;
import com.maxcinema.maxcinema.repository.TipoSalasRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TiposSalasService {
    @Autowired
    private TipoSalasRepository tipoSalasRepository;

    public List<TiposSalas> ListarTiposSalas(){
        return tipoSalasRepository.findAll();
    }

    public TiposSalas guardarTiposSalas(TiposSalas tiposSalas){
        return tipoSalasRepository.save(tiposSalas);
    }

    public String eliminarTiposDeSalas(Integer id){
        try {
            TiposSalas tiposSalas = tipoSalasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible eliminar con id, el id" + id + "no existe"));
            tipoSalasRepository.delete(tiposSalas);
            return "los tipos de sala" + tiposSalas.getNombre() + "ha sido eliminado exitosamente";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    public TiposSalas actualizar(Integer id,TiposSalas tiposSalas){
        TiposSalas tiposSalas2 = tipoSalasRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de eliminar con id, el id" + id + "no existe"));
        if(tiposSalas.getNombre() != null){
            tiposSalas2.setNombre(tiposSalas.getNombre());
        }
        return tipoSalasRepository.save(tiposSalas2);



    }
}