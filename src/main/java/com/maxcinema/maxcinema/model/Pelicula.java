package com.maxcinema.maxcinema.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El titulo no puede estar vacio")
    @Size(min = 4, max = 50, message = "El titulo de la pelicula debe tener entre 4 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String titulo;

    @NotBlank(message = "La descripcion de la pelicula no puede estar vacia")
    @Size(min = 100, max = 250, message = "La descripcion de la pelicula debe tener entre 100 y 250 caracteres")
    @Column(nullable = false, length = 250)
    private String descripcion;

    @Min(value = 60, message = "La duración mínima es 60 minutos")
    @Max(value = 240, message = "La duración máxima es 240 minutos")
    private Integer duracion;

    @Min(value = 1, message = "El año debe ser mayor a 0")
    private Integer anioEstreno;

    @Column(nullable = false)
    private String estado;
    
    @OneToMany(mappedBy = "pelicula")
    private List<Generos> generos;

    @OneToMany(mappedBy = "pelicula")
    private List<Directores> directores;

    @OneToMany(mappedBy = "pelicula")
    private List<Idiomas> idiomas;

    @OneToMany(mappedBy = "pelicula")
    private List<SalasPelicula> salasPelicula;

    @OneToMany(mappedBy = "pelicula") 
    private List<Entrada> entradas;
}
