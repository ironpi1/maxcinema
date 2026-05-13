package com.maxcinema.maxcinema.DTO;

import lombok.Data;

@Data
public class EntradaDTO {
    
    private Integer id;
    private String tipoEntrada;
    private Float precio;
    private Integer cantidad;
    private String horario;
    private String metodoPago;
    private String cliente;
    private String pelicula;

}
