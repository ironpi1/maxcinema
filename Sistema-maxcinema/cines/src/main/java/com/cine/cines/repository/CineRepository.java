package com.cine.cines.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.cines.model.Cine;

@Repository
public interface CineRepository extends JpaRepository<Cine, Integer> {
    
    List<Cine> findByNombre(String nombre);

    List<Cine> findByDireccion(String direccion);

}