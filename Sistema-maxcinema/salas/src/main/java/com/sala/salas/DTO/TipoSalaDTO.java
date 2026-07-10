package com.sala.salas.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TipoSalaDTO {
    private Integer id;
    private String nombre;
}