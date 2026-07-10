package com.pelicula.peliculas.DTO;

import java.util.List;
import com.pelicula.peliculas.model.Directores;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DirectorDTO {

    private Integer id;
    private String nombre;
    private String nacionalidad;
    private List<Directores> directores;
}