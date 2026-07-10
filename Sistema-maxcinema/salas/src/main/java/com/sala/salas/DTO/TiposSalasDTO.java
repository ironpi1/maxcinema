package com.sala.salas.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TiposSalasDTO {
    private Integer id;
    private String nombre;
    private Integer salaId;
    private String salaNombre;
    private Integer tipoSalaId;
    private String tipoSalaNombre;
}