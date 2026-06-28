package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.pelicula.peliculas.DTO.IdiomasDTO;
import com.pelicula.peliculas.controller.V2.IdiomasControllerV2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class IdiomasModelAssembler implements RepresentationModelAssembler<IdiomasDTO, EntityModel<IdiomasDTO>> {
    @Override
    public EntityModel<IdiomasDTO> toModel(IdiomasDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(IdiomasControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(IdiomasControllerV2.class).listarTodos()).withRel("idiomas-pelicula"));
    }
}
