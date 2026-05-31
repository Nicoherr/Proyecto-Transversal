package com.marketplace.vendedor.assemblers;
import com.marketplace.vendedor.controller.VendedorControllerV2;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VendedorModelAssembler implements RepresentationModelAssembler<VendedorResponseDTO, EntityModel<VendedorResponseDTO>> {

    @Override
    public EntityModel<VendedorResponseDTO> toModel(VendedorResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(VendedorControllerV2.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(VendedorControllerV2.class).listar()).withRel("vendedores")
        );
    }
}

