package com.cine.cines.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cine.cines.DTO.CineDTO;
import com.cine.cines.controller.V2.CineControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CineModelAssembler implements RepresentationModelAssembler<CineDTO, EntityModel<CineDTO>> {
    
    @Override
    public EntityModel<CineDTO> toModel(CineDTO cine) {
        return EntityModel.of(cine,
            linkTo(methodOn(com.cine.cines.controller.V2.CineControllerV2.class).buscarPorId(cine.getId())).withSelfRel(),
            linkTo(methodOn(com.cine.cines.controller.V2.CineControllerV2.class).listarCines()).withRel("cines"));
    }
}
