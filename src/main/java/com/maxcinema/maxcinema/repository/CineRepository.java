package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.Cine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CineRepository extends JpaRepository<Cine, Integer> {
    List<Cine> findByNombre(String nombre);

    List<Cine> findByDireccion(String direccion);
}