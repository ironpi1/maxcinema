package com.maxcinema.maxcinema.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maxcinema.maxcinema.model.Idioma;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Integer>{

    // Para buscar idioma por nombre exacto
    Optional<Idioma> findByNombre(String nombre);
}
