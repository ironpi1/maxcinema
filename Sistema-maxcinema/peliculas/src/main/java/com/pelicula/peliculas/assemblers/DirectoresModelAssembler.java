package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.pelicula.peliculas.DTO.DirectoresDTO;
import com.pelicula.peliculas.controller.V2.DirectoresControllerV2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DirectoresModelAssembler implements RepresentationModelAssembler<DirectoresDTO, EntityModel<DirectoresDTO>> {
    @Override
    public EntityModel<DirectoresDTO> toModel(DirectoresDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(DirectoresControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(DirectoresControllerV2.class).listarTodos()).withRel("directores-pelicula"));
    }
}
