package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pelicula.peliculas.DTO.PeliculaDTO;
import com.pelicula.peliculas.controller.V2.PeliculaControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<PeliculaDTO, EntityModel<PeliculaDTO>> {

    @Override
    public EntityModel<PeliculaDTO> toModel(PeliculaDTO pelicula) {
        return EntityModel.of(pelicula,
            linkTo(methodOn(PeliculaControllerV2.class).buscarPorId(pelicula.getId())).withSelfRel(),
            linkTo(methodOn(PeliculaControllerV2.class).listarTodas()).withRel("peliculas"));
    }
}
