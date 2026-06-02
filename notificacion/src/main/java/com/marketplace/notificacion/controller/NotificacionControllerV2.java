package com.marketplace.notificacion.controller;

import com.marketplace.notificacion.assemblers.NotificacionModelAssembler;
import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Notificaciones V2", description = "Operaciones de notificación con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionControllerV2 {

    private final NotificacionService service;
    private final NotificacionModelAssembler assembler;

    public NotificacionControllerV2(NotificacionService service, NotificacionModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todas las notificaciones con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<NotificacionResponseDTO>> listar() {
        log.info("GET /api/v2/notificaciones - Listar con HATEOAS");
        List<EntityModel<NotificacionResponseDTO>> notificaciones = service.findAllNotificaciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener notificación por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<NotificacionResponseDTO> obtener(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable long id) {
        log.info("GET /api/v2/notificaciones/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.findNotificacionesById(id));
    }

    @Operation(summary = "Crear notificación con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<NotificacionResponseDTO>> crear(@Valid @RequestBody NotificacionRequestDTO dto) {
        log.info("POST /api/v2/notificaciones - Crear con HATEOAS");
        EntityModel<NotificacionResponseDTO> model = assembler.toModel(service.makeNotificacion(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar notificación")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable long id) {
        log.info("DELETE /api/v2/notificaciones/{} - Eliminar", id);
        service.deleteNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
