package com.maxcinema.maxcinema.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comuna")

public class comuna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "el nombre no puede estar vacio")
    @Size(min = 6,max = 25,message = "minimo 6 caracteres")
    @Column(nullable = false, length = 25)
    private String nombre;

    @OneToMany(mappedBy = "region")
    private List<comuna> comunas;
}