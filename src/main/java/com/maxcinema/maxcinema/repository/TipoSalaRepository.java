package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.TipoSala;
import com.maxcinema.maxcinema.model.TiposSalas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TipoSalaRepository extends JpaRepository<TipoSala, Integer>{
    List<TipoSala> findByNombre(String nombre);
    List<TipoSala> findByTiposSalas(TiposSalas tiposSalas);
}