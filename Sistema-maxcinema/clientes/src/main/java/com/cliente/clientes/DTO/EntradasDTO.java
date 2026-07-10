package com.cliente.clientes.DTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EntradasDTO {
    private Integer id;
    private String tipoEntrada;
    private Float precio;
    private Integer cantidad;
    private String horario;
    private String metodoPago;
    private String cliente;
    private String pelicula;
}