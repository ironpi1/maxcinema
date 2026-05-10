package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.comuna;
import com.maxcinema.maxcinema.repository.ComunaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    public List<comuna> ListarComuna(){
        return comunaRepository.findAll();
    }

    public comuna guardarComuna(comuna comuna){
        return comunaRepository.save(comuna);
    }
}