package com.marketplace.valoracion.assemblers;

import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.controller.ValoracionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ValoracionModelAssembler implements RepresentationModelAssembler<ValoracionResponseDTO, EntityModel<ValoracionResponseDTO>> {

    @Override
    public EntityModel<ValoracionResponseDTO> toModel(ValoracionResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ValoracionController.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(ValoracionController.class).listar()).withRel("valoraciones")
        );
    }
}
