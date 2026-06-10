package com.pelicula.peliculas.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pelicula.peliculas.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer>{
    Optional<Genero> findByNombre(String nombre);
}
