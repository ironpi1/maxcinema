package com.maxcinema.maxcinema.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Sala")

public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "el nombre no puede quedar vacio")
    @Size(min = 5, max = 12, message = "minimo 5 caracteres")
    @Column(nullable = false, length = 12)
    private String nombre;

    @Builder.Default
    @Min(value = 1, message = "minimo un caracter")
    @Max(value = 25, message = "maximo 25")
    @Column(nullable = false)
    private Integer numeroDeSala = 1;

    @ManyToOne
    @JoinColumn(name = "cine_id")
    private Cine cine;

    @OneToMany(mappedBy = "sala")
    private List<TiposSalas> tiposSalas;

    @OneToMany(mappedBy = "sala")
    private List<SalasPelicula> salasPelicula;
}