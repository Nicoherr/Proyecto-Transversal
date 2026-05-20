package com.marketplace.reporte.controller;

import com.marketplace.reporte.DTO.DetalleReporteRequestDTO;
import com.marketplace.reporte.DTO.DetalleReporteResponseDTO;
import com.marketplace.reporte.service.DetalleReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("detalles-reporte") // Endpoint base
@RequiredArgsConstructor
public class DetalleReporteController {

    private final DetalleReporteService detalleReporteService;

    // Listar detalles de un reporte específico
    @GetMapping("/reporte/{reporteId}")
    public ResponseEntity<List<DetalleReporteResponseDTO>> getDetallesByReporte(@PathVariable Long reporteId) {
        return ResponseEntity.ok(detalleReporteService.findByReporteId(reporteId));
    }

    // Crear un detalle vinculado a un reporte
    @PostMapping
    public ResponseEntity<DetalleReporteResponseDTO> postDetalle(@Valid @RequestBody DetalleReporteRequestDTO dto) {
        DetalleReporteResponseDTO detalle = detalleReporteService.makeDetalle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalle);
    }

    // Eliminar un detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalle(@PathVariable Long id) {
        detalleReporteService.deleteDetalle(id);
        return ResponseEntity.noContent().build();
    }
}