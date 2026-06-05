package com.marketplace.pedido.assemblers;

import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.controller.PedidoController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<PedidoResponseDTO, EntityModel<PedidoResponseDTO>> {

    @Override
    public EntityModel<PedidoResponseDTO> toModel(PedidoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PedidoController.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listar()).withRel("pedidos")
        );
    }
}