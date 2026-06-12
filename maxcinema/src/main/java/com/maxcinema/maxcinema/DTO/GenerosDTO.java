package com.maxcinema.maxcinema.DTO;

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
