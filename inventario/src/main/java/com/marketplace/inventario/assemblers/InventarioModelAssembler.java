package com.marketplace.inventario.assemblers;
import com.marketplace.inventario.controller.InventarioControllerV2;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<InventarioResponseDTO, EntityModel<InventarioResponseDTO>> {

    @Override
    public EntityModel<InventarioResponseDTO> toModel(InventarioResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(InventarioControllerV2.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(InventarioControllerV2.class).listar()).withRel("inventarios")
        );
    }
}

