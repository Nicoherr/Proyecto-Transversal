package com.marketplace.notificacion.controller;

import com.marketplace.notificacion.DTO.ExceptionDTO;
import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca la clase como controlador REST (combina @Controller + @ResponseBody)
@RequestMapping("notificaciones") // Ruta base para todos los endpoints de este controlador
@RequiredArgsConstructor // Lombok: genera constructor automático para campos finales
@Tag(name = "Notificaciones", description = "Operaciones relacionadas a las notificaciones")
public class NotificacionController { // Clase controladora para manejar peticiones HTTP de notificaciones

    private final NotificacionService notificacionService; // Servicio inyectado (final para @RequiredArgsConstructor)

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener todas las notificaciones",
            description = "Retorna una lista completa de todas las notificaciones registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de notificaciones obtenida exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = NotificacionResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> getNotificaciones() {
        return ResponseEntity.ok(notificacionService.findAllNotificaciones());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener notificación por ID",
            description = "Retorna una notificación específica buscando por su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notificación encontrada exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificacionResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploNotificacion",
                                    value = "{\"id\": 1, \"asunto\": \"Oferta especial\", \"mensaje\": \"El producto que seguías bajó de precio\", \"fecha\": \"2026-06-01T10:00:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notificación no encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> getNotificacion(
            @Parameter(description = "ID único de la notificación", required = true, example = "1")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(notificacionService.findNotificacionesById(id));
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Crear una nueva notificación",
            description = "Registra y envía una nueva notificación en el sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para crear la notificación",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificacionRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploRequest",
                                    value = "{\"asunto\": \"Oferta especial\", \"mensaje\": \"El producto que seguías bajó de precio\"}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Notificación creada exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificacionResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> postNotificacion(
            @Valid @org.springframework.web.bind.annotation.RequestBody NotificacionRequestDTO notificacionDTO
    ) {
        NotificacionResponseDTO nuevo = notificacionService.makeNotificacion(notificacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Operation(
            summary = "Eliminar una notificación",
            description = "Elimina una notificación del sistema por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Notificación eliminada exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notificación no encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(
            @Parameter(description = "ID único de la notificación a eliminar", required = true, example = "1")
            @PathVariable long id
    ) {
        notificacionService.deleteNotificacion(id);
        return ResponseEntity.noContent().build();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
}
