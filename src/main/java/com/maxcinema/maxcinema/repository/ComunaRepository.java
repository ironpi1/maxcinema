package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComunaRepository extends JpaRepository <comuna, Integer> {
    List<comuna> findByNombre(String nombre);
}