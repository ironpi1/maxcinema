package com.pelicula.peliculas.DTO;

import lombok.Data;

@Data
public class SalasPeliculaDTO {

    private Integer id;
    private Integer peliculaId;
    private String peliculaTitulo;
    private Integer salaId;
}
