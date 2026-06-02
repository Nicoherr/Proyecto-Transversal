package com.marketplace.pago.assemblers;

import com.marketplace.pago.controller.PagoControllerV2;
import com.marketplace.pago.DTO.PagoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoResponseDTO, EntityModel<PagoResponseDTO>> {

    @Override
    public EntityModel<PagoResponseDTO> toModel(PagoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PagoControllerV2.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listar()).withRel("pagos")
        );
    }
}