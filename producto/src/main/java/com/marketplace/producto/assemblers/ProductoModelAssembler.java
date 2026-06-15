package com.marketplace.producto.assemblers;

import com.marketplace.producto.controller.ProductoController;
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
                linkTo(methodOn(ProductoController.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("productos")
        );
    }
}
