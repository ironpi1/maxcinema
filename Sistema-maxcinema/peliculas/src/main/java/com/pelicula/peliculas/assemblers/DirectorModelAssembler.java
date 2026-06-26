package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pelicula.peliculas.DTO.DirectorDTO;
import com.pelicula.peliculas.controller.V2.DirectorControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DirectorModelAssembler implements RepresentationModelAssembler<DirectorDTO, EntityModel<DirectorDTO>> {

    @Override
    public EntityModel<DirectorDTO> toModel(DirectorDTO director) {
        return EntityModel.of(director,
            linkTo(methodOn(DirectorControllerV2.class).buscarPorId(director.getId())).withSelfRel(),
            linkTo(methodOn(DirectorControllerV2.class).listarTodos()).withRel("directores"));
    }
}
