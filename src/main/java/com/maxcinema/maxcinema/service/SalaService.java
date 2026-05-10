package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.sala;
import com.maxcinema.maxcinema.repository.SalaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    public List<sala> Listarsala(){
        return salaRepository.findAll();
    }

    public sala guardarSala(sala sala){
        return salaRepository.save(sala);
    }
}