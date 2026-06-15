package com.marketplace.usuario.assemblers;
import com.marketplace.usuario.controller.UsuarioController;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// El assembler se encarga de tomar un UsuarioResponseDTO y envolverlo
// en un EntityModel que además incluye los links HATEOAS automáticamente
@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioResponseDTO, EntityModel<UsuarioResponseDTO>> {

    @Override
    public EntityModel<UsuarioResponseDTO> toModel(UsuarioResponseDTO dto) {
        return EntityModel.of(dto,
                // "self" es el link al usuario específico — apunta a GET /api/v2/usuario/{id}
                linkTo(methodOn(UsuarioController.class).obtener(dto.getId())).withSelfRel(),
                // "usuarios" es el link para ver todos — apunta a GET /api/v2/usuario
                linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios")
        );
    }
}
