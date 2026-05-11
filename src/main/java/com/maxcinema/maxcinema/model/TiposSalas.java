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
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipos_sala")

public class TiposSalas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "el nombre puede quedar vacio")
    @Size(min = 3, max = 14, message = "minimo 3 caracteres")
    @Column(nullable = false, length = 14)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private sala sala;

    @ManyToOne
    @JoinColumn(name = "TipoSala_id")
    private TipoSala tipoSala;
}