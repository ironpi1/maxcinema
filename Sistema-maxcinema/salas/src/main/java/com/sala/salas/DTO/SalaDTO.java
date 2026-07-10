package com.sala.salas.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SalaDTO {
    private Integer id;
    private String nombre;
    private Integer numeroDeSala;
}