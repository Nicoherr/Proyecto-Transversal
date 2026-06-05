package com.marketplace.notificacion.assemblers;

import com.marketplace.notificacion.controller.NotificacionController;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<NotificacionResponseDTO, EntityModel<NotificacionResponseDTO>> {

    @Override
    public EntityModel<NotificacionResponseDTO> toModel(NotificacionResponseDTO dto) {
         return EntityModel.of(dto,
                linkTo(methodOn(NotificacionController.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(NotificacionController.class).listar()).withRel("notificaciones")
        );
    }
}
