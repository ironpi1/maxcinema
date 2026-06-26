package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pelicula.peliculas.DTO.IdiomaDTO;
import com.pelicula.peliculas.controller.V2.IdiomaControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class IdiomaModelAssembler implements RepresentationModelAssembler<IdiomaDTO, EntityModel<IdiomaDTO>> {

    @Override
    public EntityModel<IdiomaDTO> toModel(IdiomaDTO idioma) {
        return EntityModel.of(idioma,
            linkTo(methodOn(IdiomaControllerV2.class).buscarPorId(idioma.getId())).withSelfRel(),
            linkTo(methodOn(IdiomaControllerV2.class).listarTodos()).withRel("idiomas"));
    }
}
