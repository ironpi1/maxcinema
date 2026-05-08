package com.maxcinema.maxcinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 50, message = "El nombre de la pelicula debe tener entre 4 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @Size(min = 100, max = 250, message = "La descripcion de la pelicula debe tener entre 100 y 250 caracteres")
    @Column(nullable = false, length = 250)
    private String descripcion;
}
