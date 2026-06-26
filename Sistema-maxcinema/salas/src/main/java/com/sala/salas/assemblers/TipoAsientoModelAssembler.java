package com.sala.salas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.sala.salas.DTO.TipoAsientoDTO;
import com.sala.salas.controller.v2.TipoAsientoControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TipoAsientoModelAssembler implements RepresentationModelAssembler<TipoAsientoDTO, EntityModel<TipoAsientoDTO>> {

    @Override
    public EntityModel<TipoAsientoDTO> toModel(TipoAsientoDTO tipoAsiento) {
        return EntityModel.of(tipoAsiento,
            linkTo(methodOn(TipoAsientoControllerV2.class).buscarPorId(tipoAsiento.getId())).withSelfRel(),
            linkTo(methodOn(TipoAsientoControllerV2.class).listar()).withRel("tipos-asiento"));
    }
}
