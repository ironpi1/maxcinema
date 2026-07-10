package com.sala.salas.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TipoAsientoDTO {
    private Integer id;
    private String tipo;
}