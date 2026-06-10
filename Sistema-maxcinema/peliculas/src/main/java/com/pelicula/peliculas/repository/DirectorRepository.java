package com.pelicula.peliculas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelicula.peliculas.model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer>{

    // Buscar por nombre exacto
    List<Director> findByNombre(String nombre);
}
