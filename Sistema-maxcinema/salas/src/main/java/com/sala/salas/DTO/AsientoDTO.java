package com.sala.salas.DTO;

import lombok.Data;

@Data
public class AsientoDTO {

    private Integer id;
    private Integer fila;
    private String columna;
    private Boolean estado;
    private Integer tipoAsientoId;
    private String tipoAsientoNombre;
}
