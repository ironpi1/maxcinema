package com.pelicula.peliculas.DTO;

import java.util.List;

import lombok.Data;

@Data
public class GeneroDTO {

    private Integer id;
    private String nombre;
    private List<String> peliculasTitulos;
}
