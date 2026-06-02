package com.marketplace.reporte.assemblers;

import com.marketplace.reporte.controller.ReporteControllerV2;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<ReporteResponseDTO, EntityModel<ReporteResponseDTO>> {

    @Override
    public EntityModel<ReporteResponseDTO> toModel(ReporteResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ReporteControllerV2.class).obtener(dto.getId())).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).listar()).withRel("reportes")
        );
    }
}
