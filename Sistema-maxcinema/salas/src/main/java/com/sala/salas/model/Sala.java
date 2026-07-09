package com.sala.salas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sala")
public class Sala {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "el nombre no puede quedar vacio")
    @Size(min = 5, max = 50, message = "minimo 5 caracteres, maximo 50")
    @Column(nullable = false, length = 50)
    private String nombre;

    @Builder.Default
    @Min(value = 1, message = "minimo un caracter")
    @Max(value = 25, message = "maximo 25")
    @Column(name = "numero_de_sala", nullable = false)
    private Integer numeroDeSala = 1;

    @JsonIgnore
    @OneToMany(mappedBy = "sala")
    private List<TiposSalas> tiposSalas;
}