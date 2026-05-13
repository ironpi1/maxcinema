package com.maxcinema.maxcinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxcinema.maxcinema.model.Directores;

@Repository
public interface DirectoresRepository extends JpaRepository<Directores, Integer>{

    // Buscar todas las películas de un director
    List<Directores> findByDirectorId(Integer directorId);

    // Buscar todos los directores de una película
    List<Directores> findByPeliculaId(Integer peliculaId);

}
