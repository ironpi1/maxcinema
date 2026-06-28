package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.pelicula.peliculas.DTO.GenerosDTO;
import com.pelicula.peliculas.controller.V2.GenerosControllerV2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GenerosModelAssembler implements RepresentationModelAssembler<GenerosDTO, EntityModel<GenerosDTO>> {
    @Override
    public EntityModel<GenerosDTO> toModel(GenerosDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(GenerosControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(GenerosControllerV2.class).listarTodos()).withRel("generos-pelicula"));
    }
}
