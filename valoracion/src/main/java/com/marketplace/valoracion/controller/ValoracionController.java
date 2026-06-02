package com.marketplace.valoracion.controller;

import com.marketplace.valoracion.DTO.ExceptionDTO;
import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.service.ValoracionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("valoracion")
@RequiredArgsConstructor
@Tag(name = "Valoraciones", description = "Operaciones relacionadas con las valoraciones de productos")
public class ValoracionController {

    private final ValoracionService valoracionService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener todas las valoraciones",
            description = "Retorna una lista completa de todas las valoraciones registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de valoraciones obtenida exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ValoracionResponseDTO.class))
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
    public ResponseEntity<List<ValoracionResponseDTO>> getValoraciones() {
        return ResponseEntity.ok(valoracionService.findAllValoraciones());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener valoración por ID",
            description = "Retorna una valoración específica buscando por su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Valoración encontrada exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValoracionResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploValoracion",
                                    value = "{\"id\": 1, \"numEstrella\": 5, \"recomendacion\": \"Excelente producto, lo recomiendo\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Valoración no encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> getValoracion(
            @Parameter(description = "ID único de la valoración", required = true, example = "1")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(valoracionService.findValoracionById(id));
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Crear una nueva valoración",
            description = "Registra una nueva valoración de producto en el sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para crear la valoración",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValoracionRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploRequest",
                                    value = "{\"numEstrella\": 4, \"recomendacion\": \"Muy buen producto, entrega rápida\"}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Valoración creada exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValoracionResponseDTO.class)
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
    public ResponseEntity<ValoracionResponseDTO> postValoracion(
            @Valid @RequestBody ValoracionRequestDTO newValoracion
    ) {
        ValoracionResponseDTO valoracion = valoracionService.makeValoracion(newValoracion);
        return ResponseEntity.status(HttpStatus.CREATED).body(valoracion);
    }

    // ─── PUT ──────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Actualizar una valoración existente",
            description = "Modifica los datos de una valoración existente por su ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevos datos de la valoración",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValoracionRequestDTO.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Valoración actualizada exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValoracionResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Valoración no encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> putValoracion(
            @Parameter(description = "ID único de la valoración a actualizar", required = true, example = "1")
            @PathVariable long id,
            @Valid @RequestBody ValoracionRequestDTO dto
    ) {
        return ResponseEntity.ok(valoracionService.updateValoracion(id, dto));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Operation(
            summary = "Eliminar una valoración",
            description = "Elimina una valoración del sistema por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Valoración eliminada exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Valoración no encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValoracion(
            @Parameter(description = "ID único de la valoración a eliminar", required = true, example = "1")
            @PathVariable long id
    ) {
        valoracionService.deleteValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
