package com.marketplace.notificacion.controller;


import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.assemblers.NotificacionModelAssembler;
import com.marketplace.notificacion.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Notificaciones V2", description = "Operaciones de notificación con HATEOAS")
@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler assembler;

    @Operation(summary = "Obtener todas las notificaciones", description = "Retorna lista de notificaciones con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<NotificacionResponseDTO>> listar() {
        List<EntityModel<NotificacionResponseDTO>> notificaciones = notificacionService.findAllNotificaciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener notificación por ID", description = "Retorna una notificación con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public NotificacionResponseDTO obtener(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable long id) {
        return assembler.toModel(notificacionService.findNotificacionesById(id)).getContent();
    }

    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<NotificacionResponseDTO>> crear(
            @Valid @RequestBody NotificacionRequestDTO dto) {
        NotificacionResponseDTO nueva = notificacionService.makeNotificacion(dto);
        return ResponseEntity
                .created(linkTo(methodOn(NotificacionController.class).obtener(nueva.getId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificación eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable long id) {
        notificacionService.deleteNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
