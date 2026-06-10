package com.pelicula.peliculas.DTO;

import lombok.Data;

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
