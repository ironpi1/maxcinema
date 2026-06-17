package com.cliente.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cliente.clientes.model.Cliente;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByRut(String rut);

    @Query("SELECT c FROM Cliente c WHERE c.tipoCliente.tipo = :nombreTipoCliente")
    List<Cliente> findByTipoCliente(@Param("nombreTipoCliente") String nombreTipoCliente);
}