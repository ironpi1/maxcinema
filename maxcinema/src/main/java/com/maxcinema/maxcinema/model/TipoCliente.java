package com.maxcinema.maxcinema.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tipo_cliente")
public class TipoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El tipo de cliente es obligatorio")
    @Size(max = 50, message = "El nombre del tipo de cliente no puede tener más de 50 caracteres")
    @Column(nullable = false, length = 50)
    private String tipo;

    @Size(max = 10, message = "El descuento no puede tener más de 10 caracteres")
    @Column(nullable = true, length = 10)
    private Float descuento;

    @OneToMany(mappedBy = "tipoCliente")
    private List<Cliente> clientes;
}
