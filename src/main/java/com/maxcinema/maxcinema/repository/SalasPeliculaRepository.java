package com.maxcinema.maxcinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxcinema.maxcinema.model.SalasPelicula;

@Repository
public interface SalasPeliculaRepository extends JpaRepository<SalasPelicula, Integer> {

    // Buscar todas las salas asociadas a una película
    List<SalasPelicula> findByPeliculaId(Integer peliculaId);

    // Buscar todas las películas asociadas a una sala
    List<SalasPelicula> findBySalaId(Integer salaId);
}
