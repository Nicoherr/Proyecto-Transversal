package com.marketplace.reporte.controller;

import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;


    //CREATE
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> postReporte(@Valid @RequestBody ReporteRequestDTO newReporte){
        ReporteResponseDTO reporte = reporteService.makeReporte(newReporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporte);
    }
    //BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<ReporteRequestDTO> getReporte(@PathVariable long id){
        return ResponseEntity.ok(reporteService.findReportesById(id));
    }
    //LISTAR
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> getReportes() {
        return ResponseEntity.ok(reporteService.findAllReportes());
    }
    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<ReporteRequestDTO> deleteReporte(@PathVariable long id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}
