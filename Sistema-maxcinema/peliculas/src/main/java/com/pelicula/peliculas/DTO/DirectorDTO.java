package com.pelicula.peliculas.DTO;

import java.util.List;
import com.pelicula.peliculas.model.Directores;
import lombok.Data;
@Data
public class DirectorDTO {

    private Integer id;
    private String nombre;
    private String nacionalidad;
    private List<Directores> directores;

}
