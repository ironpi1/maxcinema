package com.sala.salas.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AsientoDTO {
    private Integer id;
    private Integer fila;
    private String columna;
    private Boolean estado;
    private Integer tipoAsientoId;
    private String tipoAsientoNombre;
}