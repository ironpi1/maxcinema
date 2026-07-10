package com.pelicula.peliculas.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenerosDTO {

    private Integer id;
    // Datos de la película
    private Integer peliculaId;
    private String peliculaTitulo;
    // Datos del género
    private Integer generoId;
    private String generoNombre;
}