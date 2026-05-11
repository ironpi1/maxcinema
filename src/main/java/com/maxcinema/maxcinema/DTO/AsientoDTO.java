package com.maxcinema.maxcinema.DTO;

import lombok.Data;

@Data
public class AsientoDTO {
    private Integer id;
    private Integer fila;
    private String columna;
    private Boolean estado;
    private Integer TipoAsiento;

}
