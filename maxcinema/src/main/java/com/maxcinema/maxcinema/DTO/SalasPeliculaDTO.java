package com.maxcinema.maxcinema.DTO;

import lombok.Data;

@Data
public class SalasPeliculaDTO {

    private Integer id;
    private Integer peliculaId;
    private String peliculaTitulo;
    private Integer salaId;
    private String salaNombre;
}
