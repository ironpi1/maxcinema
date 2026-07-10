package com.cliente.clientes.DTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PeliculaExternaDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Integer duracion;
    private Integer anioEstreno;
    private String estado;
}