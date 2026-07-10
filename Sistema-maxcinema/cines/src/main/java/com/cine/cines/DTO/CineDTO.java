package com.cine.cines.DTO;

import com.cine.cines.model.Cine;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CineDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String nombreComuna;
}