package com.maxcinema.maxcinema.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maxcinema.maxcinema.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer>{

    Optional<Genero> findByNombre(String nombre);
}
