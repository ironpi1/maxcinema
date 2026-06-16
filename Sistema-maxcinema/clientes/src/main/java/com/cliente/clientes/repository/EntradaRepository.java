package com.cliente.clientes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cliente.clientes.model.Entrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Integer> {
    List<Entrada> findByPeliculaId(Integer peliculaId);
}