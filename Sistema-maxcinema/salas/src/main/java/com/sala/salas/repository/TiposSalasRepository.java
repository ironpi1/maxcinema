package com.sala.salas.repository;

import java.util.List;
import com.sala.salas.model.TiposSalas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiposSalasRepository extends JpaRepository<TiposSalas, Integer> {
    List<TiposSalas> findBySalaId(Integer salaId);
}