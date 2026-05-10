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
}