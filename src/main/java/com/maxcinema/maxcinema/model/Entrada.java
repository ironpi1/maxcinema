package com.maxcinema.maxcinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entradas")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Debe especificar el tipo")
    @Column(nullable = false, length = 10)
    private String tipoEntrada;

    @NotNull(message = "el cliente debe tener un rut")
    @Column(nullable = false, length = 9)
    private Float precio;

    @NotNull(message = "el cliente debe tener un email")
    @Column(nullable = false, length = 100)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    //@ManyToOne
    //@JoinColumn(name = "pelicula_id")
    //private Pelicula pelicula;

    
}
