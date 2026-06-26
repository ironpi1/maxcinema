package com.sala.salas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.sala.salas.DTO.AsientoDTO;
import com.sala.salas.controller.v2.AsientoControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AsientoModelAssembler implements RepresentationModelAssembler<AsientoDTO, EntityModel<AsientoDTO>> {

    @Override
    public EntityModel<AsientoDTO> toModel(AsientoDTO asiento) {
        return EntityModel.of(asiento,
            linkTo(methodOn(AsientoControllerV2.class).buscarPorId(asiento.getId())).withSelfRel(),
            linkTo(methodOn(AsientoControllerV2.class).listar()).withRel("asientos"));
    }
}
