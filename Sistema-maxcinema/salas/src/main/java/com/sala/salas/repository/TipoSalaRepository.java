package com.sala.salas.repository;

import com.sala.salas.model.TipoSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSalaRepository extends JpaRepository<TipoSala, Integer> {
}
