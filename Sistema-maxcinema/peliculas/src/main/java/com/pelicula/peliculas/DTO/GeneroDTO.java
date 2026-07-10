package com.pelicula.peliculas.DTO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GeneroDTO {

    private Integer id;
    private String nombre;
    private List<String> peliculasTitulos;
}