package com.cliente.clientes.DTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TiposDeClientesDTO {
    private Integer id;
    private String tipo;
    private Float descuento;
}