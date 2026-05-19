package com.marketplace.reporte.controller;

import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> getReportes() {
        return ResponseEntity.ok(reporteService.findAllReportes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> getReporte(@PathVariable long id) {
        return ResponseEntity.ok(reporteService.findReportesById(id));
    }

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> postReporte(@Valid @RequestBody ReporteRequestDTO newReporte) {
        ReporteResponseDTO reporte = reporteService.makeReporte(newReporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable long id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}
