package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.cine;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;//
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CineRepository extends JpaRepository<cine, Integer> {
    List<cine> findByNombre(String nombre);

    List<cine> findByDireccion(String direccion);
}