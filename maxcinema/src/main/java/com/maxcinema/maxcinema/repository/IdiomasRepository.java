package com.maxcinema.maxcinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maxcinema.maxcinema.model.Idiomas;

@Repository
public interface IdiomasRepository extends JpaRepository<Idiomas, Integer> {
    // Buscar todos los idiomas asociados a una película
    List<Idiomas> findByPeliculaId(Integer peliculaId);

    // Buscar todas las películas asociadas a un idioma
    List<Idiomas> findByIdiomaId(Integer idiomaId);
}