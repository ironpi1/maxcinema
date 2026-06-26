package com.sala.salas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.sala.salas.DTO.TiposSalasDTO;
import com.sala.salas.controller.v2.TiposSalasControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TiposSalasModelAssembler implements RepresentationModelAssembler<TiposSalasDTO, EntityModel<TiposSalasDTO>> {

    @Override
    public EntityModel<TiposSalasDTO> toModel(TiposSalasDTO tiposSalas) {
        return EntityModel.of(tiposSalas,
            linkTo(methodOn(TiposSalasControllerV2.class).buscarPorId(tiposSalas.getId())).withSelfRel(),
            linkTo(methodOn(TiposSalasControllerV2.class).listar()).withRel("tipos-salas"));
    }
}
