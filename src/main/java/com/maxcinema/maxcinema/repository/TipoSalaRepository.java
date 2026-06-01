package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.TipoSala;
import com.maxcinema.maxcinema.model.TiposSalas;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
@Repository
public interface TipoSalaRepository extends JpaRepository<TipoSala, Integer>{
    List<TipoSala> findByNombre(String nombre);
    List<TipoSala> findByTiposSalas(TiposSalas tiposSalas);
}