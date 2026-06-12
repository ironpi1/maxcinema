package com.pelicula.peliculas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pelicula.peliculas.model.Generos;

public interface GenerosRepository extends JpaRepository<Generos, Integer> {

    // Buscar todos los géneros asociados a una película
    List<Generos> findByPeliculaId(Integer peliculaId);

    // Buscar todas las películas asociadas a un género
    List<Generos> findByGeneroId(Integer generoId);
}
