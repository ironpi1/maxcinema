package com.cliente.clientes.assemblers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cliente.clientes.DTO.ClienteDTO;
import com.cliente.clientes.controller.V2.ClienteControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler <ClienteDTO, EntityModel<ClienteDTO>> {
    @Override
    public EntityModel<ClienteDTO> toModel(ClienteDTO clienteDTO){
        return EntityModel.of(clienteDTO,
            linkTo(methodOn(ClienteControllerV2.class).buscarPorId(clienteDTO.getId())).withSelfRel(),
            linkTo(methodOn(ClienteControllerV2.class).todosLosClientes()).withRel("clientes"));
    }
}