package com.maxcinema.maxcinema.DTO;
import lombok.Data;

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
