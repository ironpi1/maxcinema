package com.pelicula.peliculas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.pelicula.peliculas.DTO.SalasPeliculaDTO;
import com.pelicula.peliculas.controller.V2.SalasPeliculaControllerV2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SalasPeliculaModelAssembler implements RepresentationModelAssembler<SalasPeliculaDTO, EntityModel<SalasPeliculaDTO>> {
    @Override
    public EntityModel<SalasPeliculaDTO> toModel(SalasPeliculaDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(SalasPeliculaControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(SalasPeliculaControllerV2.class).listarTodos()).withRel("salas-pelicula"));
    }
}
