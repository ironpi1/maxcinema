package com.cliente.clientes.assemblers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.cliente.clientes.DTO.EntradasDTO;
import com.cliente.clientes.controller.V2.EntradaControllerV2;

@Component
public class EntradasModelAssembler implements RepresentationModelAssembler<EntradasDTO, EntityModel<EntradasDTO>> {
    @Override
    public EntityModel<EntradasDTO> toModel(EntradasDTO entradasDTO){
        return EntityModel.of(entradasDTO,
            linkTo(methodOn(EntradaControllerV2.class).buscarPorId(entradasDTO.getId())).withSelfRel(),
            linkTo(methodOn(EntradaControllerV2.class).todosLasEntradas()).withRel("entradas"));
    }
}