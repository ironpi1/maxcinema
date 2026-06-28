package com.cine.cines.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cine.cines.DTO.RegionDTO;
import com.cine.cines.controller.V2.RegionControllerV2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    public EntityModel<RegionDTO> toModel(RegionDTO region) {
        return EntityModel.of(region,
            linkTo(methodOn(RegionControllerV2.class).buscarPorId(region.getRegion_id())).withSelfRel(),
            linkTo(methodOn(RegionControllerV2.class).listarRegiones()).withRel("regiones"));
    }

}
