package com.pelicula.peliculas.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del genero no puede estar vacio")
    @Size(min = 10, max = 50, message = "El nombre de la pelicula debe tener entre 4 y 50 caracteres")
    private String nombre;

    // Relación hacia la tabla puente
    @JsonIgnore
    @OneToMany(mappedBy = "genero")
    private List<Generos> peliculas;
}
