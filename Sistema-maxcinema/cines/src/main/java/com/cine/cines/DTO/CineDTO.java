package com.cine.cines.DTO;

import com.cine.cines.model.Cine;
import lombok.Data;

@Data
public class CineDTO {
    
    private Integer id;
    private String nombre;
    private String direccion;
    private String nombreComuna;

}
