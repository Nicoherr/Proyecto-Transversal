package com.marketplace.carrito.assemblers;
import com.marketplace.carrito.controller.CarritoControllerV2;
import com.marketplace.carrito.dto.CarritoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// El assembler toma un CarritoResponseDTO y le agrega los links HATEOAS
// Spring lo detecta automáticamente con @Component
@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<CarritoResponseDTO, EntityModel<CarritoResponseDTO>> {

    @Override
    public EntityModel<CarritoResponseDTO> toModel(CarritoResponseDTO dto) {
        return EntityModel.of(dto,
                // "self" apunta al carrito específico
                linkTo(methodOn(CarritoControllerV2.class).obtener(dto.getId())).withSelfRel(),
                // "carritos" apunta a... no hay listar en carrito, así que apuntamos a productos
                linkTo(methodOn(CarritoControllerV2.class).listarProductos(dto.getId())).withRel("productos")
        );
    }
}
