package com.marketplace.notificacion.controller;

import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    //Validaciones para listar todas las notificaciones

    @ArraySchema(schema = @Schema(implementation = Notificacion.class))
    private List<Notificacion> notificaciones;

    @Operation(summary = "Obtener todas las notificaciones", description = "Obtiene una lista de todas las notificaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "", description = "")
    })
    @GetMapping// Maneja peticiones GET a la ruta base (sin parámetros)
    //Cuerpo de la solicitud
    public ResponseEntity<List<NotificacionResponseDTO>> getNotificaciones() { // Retorna lista de todas las notificaciones con código HTTP 200 OK
        return ResponseEntity.ok(notificacionService.findAllNotificaciones());
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Validaciones para listar una notificacion por su ID
    @Parameter(description = "ID de la notificacion", required = true)
    @Operation(summary = "Obtener una notificacion por su id", description = "Obtiene una notificacion de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "", description = "")
    })
    @GetMapping("/{id}") // Maneja peticiones GET con un ID en la URL (ej: /api/v1/notificaciones/5)
    //Cuerpo de la solicitud
    public ResponseEntity<NotificacionResponseDTO> getNotificacion(@PathVariable long id) { // Retorna una notificación específica por ID con código HTTP 200 OK
        return ResponseEntity.ok(notificacionService.findNotificacionesById(id));
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Validaciones para crear una nueva notificacion
        @Schema(description = "Entidad que representa una notificacion")
        public class notificacion {
        @Schema(description = "Asunto de la notificacion", example = "Oferta")
        private String codigo;
        @Schema(description = "Mensaje de la notificacion", example = "El producto que querias bajo de precio")
        private String nombre;
    }

    @Parameter(description = "Crea una notificacion nueva con todas sus propiedades", required = true)
    @Operation(summary = "Crea una notificacion", description = "Obtiene una notificacion y la agrega a la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "", description = "")
    })
    @PostMapping// Maneja peticiones POST (crear nuevo recurso)
    //Cuerpo de la solicitud
    public ResponseEntity<NotificacionResponseDTO> postNotificacion(@Valid @RequestBody NotificacionRequestDTO notificacionDTO) { // @Valid: activa validaciones, @RequestBody: del JSON
        NotificacionResponseDTO nuevo = notificacionService.makeNotificacion(notificacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // Retorna 201 CREATED con el objeto creado
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Validaciones para eliminar una notificacion por su ID
    @Parameter(description = "ID de la notificacion", required = true)
    @Operation(summary = "Eliminar una notificacion por su ID", description = "Elimina una notificacion de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "", description = "")
    })
    @DeleteMapping("/{id}") // Maneja peticiones DELETE con un ID en la URL
    //Cuerpo de la solicitud
    public ResponseEntity<Void> deleteNotificacion(@PathVariable long id) {
        notificacionService.deleteNotificacion(id); // Elimina la notificación (no retorna contenido)
        return ResponseEntity.noContent().build(); // Retorna 204 NO CONTENT (éxito sin cuerpo)
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
}
