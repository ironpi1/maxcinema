package com.maxcinema.maxcinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxcinema.maxcinema.model.TipoAsiento;

@Repository
public interface TipoAsientoRepository extends JpaRepository<TipoAsiento, Integer> {

}
