package com.maxcinema.maxcinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    
}
