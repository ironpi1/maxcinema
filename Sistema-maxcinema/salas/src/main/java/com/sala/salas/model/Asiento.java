package com.sala.salas.model;
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
    @Size(min = 1, max = 2, message = "La fila no puede tener más de 2 dígitos")
    @Column(nullable = false, length = 2)
    private Integer fila;

    @NotBlank (message = "La columna es obligatoria")
    @Size(max = 1, message = "La columna no puede tener más de 1 caracter")
    @Column(nullable = false, length = 1)
    private String columna;
    
    @NotBlank (message = "El estado es obligatorio")
    @Size(min = 1, max = 20, message = "El estado no puede tener más de 20 caracteres")
    @Column(nullable = false, length = 20)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "tipo_asiento_id")
    private TipoAsiento tipoAsiento;

}
