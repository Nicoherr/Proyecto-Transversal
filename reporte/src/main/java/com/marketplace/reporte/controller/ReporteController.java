package com.marketplace.reporte.controller;

import com.marketplace.reporte.DTO.ExceptionDTO;
import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.service.ReporteService;
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

@RestController
@RequestMapping("reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Operaciones relacionadas con la generación y consulta de reportes de ventas")
public class ReporteController {

    private final ReporteService reporteService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener todos los reportes",
            description = "Retorna una lista completa de todos los reportes generados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de reportes obtenida exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReporteResponseDTO.class))
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
    public ResponseEntity<List<ReporteResponseDTO>> getReportes() {
        return ResponseEntity.ok(reporteService.findAllReportes());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener reporte por ID",
            description = "Retorna un reporte específico buscando por su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporte encontrado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReporteResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploReporte",
                                    value = "{\"id\": 1, \"tipo\": \"Ventas mensuales\", \"descripcion\": \"Resumen de ventas del mes de junio\", \"fecha\": \"2026-06-01T09:00:00\", \"estado\": true}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reporte no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> getReporte(
            @Parameter(description = "ID único del reporte", required = true, example = "1")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(reporteService.findReportesById(id));
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Generar un nuevo reporte",
            description = "Crea y registra un nuevo reporte en el sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para generar el reporte",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReporteRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploRequest",
                                    value = "{\"tipo\": \"Ventas mensuales\", \"descripcion\": \"Resumen de ventas del mes de junio\"}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Reporte generado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReporteResponseDTO.class)
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
    public ResponseEntity<ReporteResponseDTO> postReporte(
            @Valid @RequestBody ReporteRequestDTO newReporte
    ) {
        ReporteResponseDTO reporte = reporteService.makeReporte(newReporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporte);
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Operation(
            summary = "Eliminar un reporte",
            description = "Elimina un reporte del sistema por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Reporte eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reporte no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(
            @Parameter(description = "ID único del reporte a eliminar", required = true, example = "1")
            @PathVariable long id
    ) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}
