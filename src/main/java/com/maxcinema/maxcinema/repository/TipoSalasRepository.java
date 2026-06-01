package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.TipoSala;
import com.maxcinema.maxcinema.model.TiposSalas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TipoSalasRepository extends JpaRepository<TiposSalas, Integer>{
    List<TiposSalas> findByNombre(String nombre);

    List<TiposSalas> findByTipoSala(TipoSala tipoSala);
}