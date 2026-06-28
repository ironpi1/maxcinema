package com.cliente.clientes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nombre del cliente es obligatorio")
    @Size(min = 3, max = 100, message = "mínimo de 3 caracteres")
    private String nombre;

    @NotBlank(message = "el cliente debe tener un rut")
    @Size(min = 8, max = 12, message = "entre 8 y 12 caracteres")
    private String rut;

    @NotBlank(message = "el cliente debe tener un email")
    @Size(min = 10, max = 100, message = "entre 10 y 100 caracteres")
    private String email;

    @NotBlank(message = "el cliente debe tener un telefono")
    @Size(min = 8, max = 15, message = "entre 8 y 15 caracteres")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "tipo_cliente_id")
    private TipoCliente tipoCliente;
}