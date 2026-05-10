package com.maxcinema.maxcinema.dto;

import java.util.List;

import lombok.Data;

@Data
public class IdiomaDTO {

    private Integer id;
    private String nombre;
    private List<String> peliculasTitulos;
}
