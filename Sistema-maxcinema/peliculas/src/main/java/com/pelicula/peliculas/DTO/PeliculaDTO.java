package com.pelicula.peliculas.DTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PeliculaDTO {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Integer duracion;
    private Integer anioEstreno;
    private String estado;
    private String nombreGeneros;
    private String nombreDirectores;
    private String nombreIdiomas;
    private String nombreSalasPelicula;
    private String entradaComprada;
}