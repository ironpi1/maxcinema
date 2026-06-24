package com.sala.salas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.sala.salas.DTO.TipoSalaDTO;
import com.sala.salas.controller.v2.TipoSalaControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TipoSalaModelAssembler implements RepresentationModelAssembler<TipoSalaDTO, EntityModel<TipoSalaDTO>> {

    @Override
    public EntityModel<TipoSalaDTO> toModel(TipoSalaDTO tipoSala) {
        return EntityModel.of(tipoSala,
            linkTo(methodOn(TipoSalaControllerV2.class).buscarPorId(tipoSala.getId())).withSelfRel(),
            linkTo(methodOn(TipoSalaControllerV2.class).listar()).withRel("tipos-sala"));
    }
}
