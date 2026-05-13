package com.maxcinema.maxcinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxcinema.maxcinema.model.Asiento;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Integer> {
    
    List<Asiento> findByEstado(Boolean estado);

}