package com.maxcinema.maxcinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxcinema.maxcinema.model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer>{

    // Buscar por nombre exacto
    List<Director> findByNombre(String nombre);
}
