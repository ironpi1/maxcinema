package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer>{
    List<Sala> findByNombre(String nombre);

    List<Sala> findByNumeroDeSala(Integer numeroDeSala);
}