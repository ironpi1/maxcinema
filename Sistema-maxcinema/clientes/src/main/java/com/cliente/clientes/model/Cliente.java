package com.cliente.clientes.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nombre del ciente es obligatorio")
    @Size(min = 5, max = 100, message = "minimo de 5 caracteres")
    private String nombre;

    @NotBlank(message = "el cliente debe tener un rut")
    @Size(min = 9,max = 9,message = "maximo 9 caracteres")
    private String rut;

    @NotBlank(message = "el cliente debe tener un email")
    @Size(min = 20,max = 100,message = "maximo de 100 caracteres")
    private String email;

    @NotBlank(message = "el cliente debe tener un telefono")
    @Size(min = 15,max = 15,message = "maximo 15 caracteres")
    private String telefono;

    //@OneToMany(mappedBy = "cliente")
    //private List<Entrada> entradas;

    @ManyToOne
    @JoinColumn(name = "tipo_cliente_id")
    private TipoCliente tipoCliente;
}