package com.marketplace.reporte.controller;

import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.assemblers.ReporteModelAssembler;
import com.marketplace.reporte.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Reportes", description = "Operaciones relacionadas con la generación y consulta de reportes")
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteModelAssembler assembler;

    @Operation(summary = "Obtener todos los reportes", description = "Retorna lista de reportes con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ReporteResponseDTO>> listar() {
        List<EntityModel<ReporteResponseDTO>> reportes = reporteService.findAllReportes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener reporte por ID", description = "Retorna un reporte con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ReporteResponseDTO obtener(
            @Parameter(description = "ID del reporte", required = true, example = "1")
            @PathVariable long id) {
        return assembler.toModel(reporteService.findReportesById(id)).getContent();
    }

    @Operation(summary = "Generar un nuevo reporte", description = "Crea y registra un nuevo reporte en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ReporteResponseDTO>> crear(
            @Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO nuevo = reporteService.makeReporte(dto);
        return ResponseEntity
                .created(linkTo(methodOn(ReporteController.class).obtener(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Eliminar un reporte", description = "Elimina un reporte del sistema por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del reporte a eliminar", required = true, example = "1")
            @PathVariable long id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}