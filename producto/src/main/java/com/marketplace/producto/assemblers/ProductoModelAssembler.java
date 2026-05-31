package com.marketplace.producto.assemblers;

import com.marketplace.producto.controller.ProductoControllerV2;
import com.marketplace.producto.dto.ProductoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<ProductoResponseDTO, EntityModel<ProductoResponseDTO>> {

    @Override
    public EntityModel<ProductoResponseDTO> toModel(ProductoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ProductoControllerV2.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).listar()).withRel("productos")
        );
    }
}
