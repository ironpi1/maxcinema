package com.maxcinema.maxcinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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
@Table(name = "asientos")
public class Asiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank (message = "La fila es obligatoria")
    @Size(max = 1, message = "La fila no puede tener más de 1 caracter")
    @Column(nullable = false, length = 1)
    private String fila;

    @NotBlank (message = "El número es obligatorio")
    @Size(max = 3, message = "El número no puede tener más de 3 digitos")
    @Column(nullable = false)
    private Integer numero;
    
    @NotBlank (message = "El estado es obligatorio")
    @Size(min = 1, max = 20, message = "El estado no puede tener más de 20 caracteres")
    @Column(nullable = false, length = 20)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "tipo_asiento_id")
    private TipoAsiento tipoAsiento;

}
