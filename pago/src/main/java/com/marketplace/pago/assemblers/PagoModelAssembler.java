package com.marketplace.pago.assemblers;

import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.controller.PagoController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoResponseDTO, EntityModel<PagoResponseDTO>> {

    @Override
    public EntityModel<PagoResponseDTO> toModel(PagoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PagoController.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(PagoController.class).listar()).withRel("pagos")
        );
    }
}