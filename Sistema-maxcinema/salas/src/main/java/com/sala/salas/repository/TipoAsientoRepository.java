package com.sala.salas.repository;

import com.sala.salas.model.TipoAsiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAsientoRepository extends JpaRepository<TipoAsiento, Integer> {
}