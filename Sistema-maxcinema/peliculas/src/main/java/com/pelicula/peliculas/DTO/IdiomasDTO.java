package com.pelicula.peliculas.DTO;

import lombok.Data;

@Data
public class IdiomasDTO {

    private Integer id;
    private Integer peliculaId;
    private String peliculaTitulo;
    private Integer idiomaId;
    private String idiomaNombre;
}
