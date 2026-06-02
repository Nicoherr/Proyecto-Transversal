package com.marketplace.reporte.controller;

import com.marketplace.reporte.assemblers.ReporteModelAssembler;
import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.service.ReporteService;
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

@Tag(name = "Reportes V2", description = "Operaciones de reporte con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/v2/reportes")
public class ReporteControllerV2 {

    private final ReporteService service;
    private final ReporteModelAssembler assembler;

    public ReporteControllerV2(ReporteService service, ReporteModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los reportes con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<ReporteResponseDTO>> listar() {
        log.info("GET /api/v2/reportes - Listar con HATEOAS");
        List<EntityModel<ReporteResponseDTO>> reportes = service.findAllReportes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteControllerV2.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener reporte por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<ReporteResponseDTO> obtener(
            @Parameter(description = "ID del reporte", required = true, example = "1")
            @PathVariable long id) {
        log.info("GET /api/v2/reportes/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.findReportesById(id));
    }

    @Operation(summary = "Generar reporte con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ReporteResponseDTO>> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        log.info("POST /api/v2/reportes - Crear con HATEOAS");
        EntityModel<ReporteResponseDTO> model = assembler.toModel(service.makeReporte(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar reporte")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reporte", required = true, example = "1")
            @PathVariable long id) {
        log.info("DELETE /api/v2/reportes/{} - Eliminar", id);
        service.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}