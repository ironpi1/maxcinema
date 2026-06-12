package com.maxcinema.maxcinema.model;

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

    @NotBlank(message = "Nombre del ciente es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "el cliente debe tener un rut")
    @Column(nullable = false, length = 9)
    private String rut;

    @NotBlank(message = "el cliente debe tener un email")
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank(message = "el cliente debe tener un telefono")
    @Column(nullable = false, length = 15)
    private String telefono;

    @OneToMany(mappedBy = "cliente")
    private List<Entrada> entradas;

    @ManyToOne
    @JoinColumn(name = "tipo_cliente_id")
    private TipoCliente tipoCliente;

}
