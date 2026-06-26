package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pelicula.peliculas.DTO.GeneroDTO;
import com.pelicula.peliculas.controller.v2.GeneroControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GeneroModelAssembler implements RepresentationModelAssembler<GeneroDTO, EntityModel<GeneroDTO>> {

    @Override
    public EntityModel<GeneroDTO> toModel(GeneroDTO genero) {
        return EntityModel.of(genero,
            linkTo(methodOn(GeneroControllerV2.class).buscarPorId(genero.getId())).withSelfRel(),
            linkTo(methodOn(GeneroControllerV2.class).listarTodos()).withRel("generos"));
    }
}
