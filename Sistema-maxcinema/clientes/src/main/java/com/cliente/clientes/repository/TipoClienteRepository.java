package com.cliente.clientes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cliente.clientes.model.TipoCliente;

@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Integer> {
    @Query("SELECT t FROM TipoCliente t WHERE t.tipo = :tipo")
    
    public List<TipoCliente> findByTipo(String tipo);
}