package com.cliente.clientes.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.cliente.clientes.DTO.MetodosDePagoDTO;
import com.cliente.clientes.controller.V2.MetodoDePagoControllerV2;

@Component
public class MetodoDePagoAssembler implements RepresentationModelAssembler<MetodosDePagoDTO, EntityModel<MetodosDePagoDTO>>{
    @Override
    public EntityModel<MetodosDePagoDTO> toModel(MetodosDePagoDTO metodosDePagoDTO){
        return EntityModel.of(metodosDePagoDTO,
            linkTo(methodOn(MetodoDePagoControllerV2.class).buscarPorId(metodosDePagoDTO.getId())).withSelfRel(),
            linkTo(methodOn(MetodoDePagoControllerV2.class).todosLosMetodosDePago()).withRel("metodosDePago"));
    }
}