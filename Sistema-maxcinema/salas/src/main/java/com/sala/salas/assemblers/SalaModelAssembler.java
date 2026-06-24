package com.sala.salas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.sala.salas.DTO.SalaDTO;
import com.sala.salas.controller.v2.SalaControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SalaModelAssembler implements RepresentationModelAssembler<SalaDTO, EntityModel<SalaDTO>> {

    @Override
    public EntityModel<SalaDTO> toModel(SalaDTO sala) {
        return EntityModel.of(sala,
            linkTo(methodOn(SalaControllerV2.class).buscarPorId(sala.getId())).withSelfRel(),
            linkTo(methodOn(SalaControllerV2.class).listar()).withRel("salas"));
    }
}
