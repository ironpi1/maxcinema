package com.cine.cines.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.cine.cines.DTO.ComunaDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ComunaModelAssembler implements org.springframework.hateoas.server.RepresentationModelAssembler<com.cine.cines.DTO.ComunaDTO, org.springframework.hateoas.EntityModel<com.cine.cines.DTO.ComunaDTO>> {

    @Override
    public EntityModel<ComunaDTO> toModel(ComunaDTO comuna) {
        return EntityModel.of(comuna,
            linkTo(methodOn(com.cine.cines.controller.V2.ComunaControllerV2.class).buscarPorId(comuna.getComuna_id())).withSelfRel(),
            linkTo(methodOn(com.cine.cines.controller.V2.ComunaControllerV2.class).listarComunas()).withRel("comunas"));

    }

}
