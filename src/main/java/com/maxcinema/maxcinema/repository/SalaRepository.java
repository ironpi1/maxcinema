package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SalaRepository extends JpaRepository<sala, Integer>{
    List<sala> findByNombre(String nombre);

    List<sala> findByNumeroDeSala(Integer numeroDeSala);
}