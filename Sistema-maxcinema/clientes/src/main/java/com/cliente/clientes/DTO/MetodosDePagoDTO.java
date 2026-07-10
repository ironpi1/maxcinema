package com.cliente.clientes.DTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MetodosDePagoDTO {
    private Integer id;
    private String nombreTipo;
}