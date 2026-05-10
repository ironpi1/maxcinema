package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.TipoSala;
import com.maxcinema.maxcinema.model.TiposSalas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TipoSalasRepository extends JpaRepository<TiposSalas, Integer>{
    List<TiposSalas> findByNombre(String nombre);

    List<TiposSalas> findByTipoSala(TipoSala tipoSala);

    Optional<TiposSalas> findById(Integer id);
}