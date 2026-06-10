package com.pelicula.peliculas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelicula.peliculas.model.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
    
    List<Pelicula> findByEstado(String estado);
    List<Pelicula> findByAnioEstreno(Integer anioEstreno);
    List<Pelicula> findByTitulo(String titulo);
}

