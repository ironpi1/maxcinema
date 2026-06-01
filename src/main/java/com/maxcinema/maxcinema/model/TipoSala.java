package com.maxcinema.maxcinema.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
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
@Table(name = "tipo_sala")

public class TipoSala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "el nombre puede quedar vacio")
    @Size(min = 3, max = 17, message = "minimo 3 caracteres")
    @Column(nullable = false, length = 17)
    private String nombre;

    @OneToMany(mappedBy = "tipoSala")
    private List<TiposSalas> tiposSalas;
}