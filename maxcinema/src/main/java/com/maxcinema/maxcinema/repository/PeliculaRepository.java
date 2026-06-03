package com.maxcinema.maxcinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxcinema.maxcinema.model.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
    
    List<Pelicula> findByEstado(String estado);
    List<Pelicula> findByAnioEstreno(Integer anioEstreno);
    List<Pelicula> findByTitulo(String titulo);
}

