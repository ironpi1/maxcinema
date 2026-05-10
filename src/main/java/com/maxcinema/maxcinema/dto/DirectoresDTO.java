package com.maxcinema.maxcinema.dto;

import lombok.Data;

@Data
public class DirectoresDTO {

    private Integer id;
    private Integer directorId;
    private String directorNombre;
    private Integer peliculaId;
    private String peliculaTitulo;
}
