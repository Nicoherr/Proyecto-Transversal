package com.marketplace.reporte.controller;

import com.marketplace.reporte.DTO.DetalleReporteRequestDTO;
import com.marketplace.reporte.DTO.DetalleReporteResponseDTO;
import com.marketplace.reporte.service.DetalleReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Detalles de Reporte", description = "Operaciones relacionadas con los detalles de cada reporte")
@RestController
@RequestMapping("/detalles-reporte")
public class DetalleReporteController {

    @Autowired
    private DetalleReporteService detalleReporteService;

    @Operation(summary = "Obtener detalles por reporte", description = "Retorna todos los detalles de un reporte específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/reporte/{reporteId}")
    public ResponseEntity<List<DetalleReporteResponseDTO>> getDetallesByReporte(
            @Parameter(description = "ID del reporte", required = true, example = "1")
            @PathVariable Long reporteId) {
        return ResponseEntity.ok(detalleReporteService.findByReporteId(reporteId));
    }

    @Operation(summary = "Crear detalle de reporte", description = "Agrega un nuevo detalle a un reporte existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<DetalleReporteResponseDTO> postDetalle(
            @Valid @RequestBody DetalleReporteRequestDTO dto) {
        return ResponseEntity.status(201).body(detalleReporteService.makeDetalle(dto));
    }

    @Operation(summary = "Eliminar detalle de reporte", description = "Elimina un detalle de reporte por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalle(
            @Parameter(description = "ID del detalle a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        detalleReporteService.deleteDetalle(id);
        return ResponseEntity.noContent().build();
    }
}