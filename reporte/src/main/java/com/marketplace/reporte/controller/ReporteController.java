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
    public ResponseEntity<ReporteResponseDTO> guardar(@Valid @RequestBody ReporteRequestDTO reporteDTO){
        ReporteResponseDTO nuevo = reporteService.createReporte(reporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    //READ
    //BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<ReporteRequestDTO> buscar(@PathVariable Long id){
        try{
            ReporteRequestDTO reporte = reporteService.findReportesById(id);
            return ResponseEntity.ok(reporte);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
    //LISTAR
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> getReportes() {
        return ResponseEntity.ok(reporteService.findAllReportes);

    }
    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}
