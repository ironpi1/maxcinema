package com.cliente.clientes.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cliente.clientes.DTO.TiposDeClientesDTO;
import com.cliente.clientes.controller.V2.TipoClienteControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TipoClienteAssembler implements RepresentationModelAssembler<TiposDeClientesDTO, EntityModel<TiposDeClientesDTO>>{
    @Override
    public EntityModel<TiposDeClientesDTO> toModel(TiposDeClientesDTO tiposDeClientesDTO){
        return EntityModel.of(tiposDeClientesDTO,
            linkTo(methodOn(TipoClienteControllerV2.class).buscarPorId(tiposDeClientesDTO.getId())).withSelfRel(),
            linkTo(methodOn(TipoClienteControllerV2.class).TodosLosClientes()).withRel("TipoCliente"));
    }
}