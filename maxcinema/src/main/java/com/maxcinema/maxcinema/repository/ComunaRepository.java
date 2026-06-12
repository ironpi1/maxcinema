package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
public interface ComunaRepository extends JpaRepository <Comuna, Integer> {
    List<Comuna> findByNombre(String nombre);
}