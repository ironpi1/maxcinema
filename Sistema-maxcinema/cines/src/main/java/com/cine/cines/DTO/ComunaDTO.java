package com.cine.cines.DTO;

import com.cine.cines.model.Comuna;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ComunaDTO {
    private Integer comuna_id;
    private String nombre;
}