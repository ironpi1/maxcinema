package com.pelicula.peliculas.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class IdiomasDTO {

    private Integer id;
    private Integer peliculaId;
    private String peliculaTitulo;
    private Integer idiomaId;
    private String idiomaNombre;
}