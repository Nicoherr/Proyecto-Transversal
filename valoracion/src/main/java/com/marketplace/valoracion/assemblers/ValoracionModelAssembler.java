package com.marketplace.valoracion.assemblers;

import com.marketplace.valoracion.controller.ValoracionControllerV2;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ValoracionModelAssembler implements RepresentationModelAssembler<ValoracionResponseDTO, EntityModel<ValoracionResponseDTO>> {

    @Override
    public EntityModel<ValoracionResponseDTO> toModel(ValoracionResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ValoracionControllerV2.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(ValoracionControllerV2.class).listar()).withRel("valoraciones")
        );
    }
}
