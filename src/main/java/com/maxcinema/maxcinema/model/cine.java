package com.maxcinema.maxcinema.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Cine")

public class Cine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "el nombre no puede quedar vacio")
    @Size(min = 5, max = 12, message = "debe tener minimo 5 caracteres")
    @Column(nullable = false,length = 12)
    private String nombre;

    @NotBlank(message = "la direccion no puede quedar vacia")
    @Size(min = 3, max = 20, message = "minimo 3 caracteres")
    @Column(nullable = false, length = 20)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;
}