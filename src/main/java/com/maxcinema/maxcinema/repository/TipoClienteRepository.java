package com.maxcinema.maxcinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxcinema.maxcinema.model.TipoCliente;

@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Integer> {

    

}
