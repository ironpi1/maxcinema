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
}