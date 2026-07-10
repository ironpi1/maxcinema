package com.cliente.clientes.DTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String rut;
    private String email;
    private String telefono;
    private String tipoCliente;
}