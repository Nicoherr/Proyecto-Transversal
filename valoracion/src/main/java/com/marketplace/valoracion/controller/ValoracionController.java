package com.marketplace.valoracion.controller;

import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.assemblers.ValoracionModelAssembler;
import com.marketplace.valoracion.service.ValoracionService;
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

@Tag(name = "Valoraciones", description = "Operaciones relacionadas con las valoraciones de productos")
@RestController
@RequestMapping("/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private ValoracionModelAssembler assembler;

    @Operation(summary = "Obtener todas las valoraciones", description = "Retorna lista de valoraciones con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ValoracionResponseDTO>> listar() {
        List<EntityModel<ValoracionResponseDTO>> valoraciones = valoracionService.findAllValoraciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(valoraciones,
                linkTo(methodOn(ValoracionController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener valoración por ID", description = "Retorna una valoración con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoración encontrada"),
            @ApiResponse(responseCode = "404", description = "Valoración no encontrada")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ValoracionResponseDTO obtener(
            @Parameter(description = "ID de la valoración", required = true, example = "1")
            @PathVariable long id) {
        return assembler.toModel(valoracionService.findValoracionById(id)).getContent();
    }

    @Operation(summary = "Crear una valoración", description = "Registra una nueva valoración de producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Valoración creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ValoracionResponseDTO>> crear(
            @Valid @RequestBody ValoracionRequestDTO dto) {
        ValoracionResponseDTO nueva = valoracionService.makeValoracion(dto);
        return ResponseEntity
                .created(linkTo(methodOn(ValoracionController.class).obtener(nueva.getId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @Operation(summary = "Actualizar una valoración", description = "Modifica los datos de una valoración existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoración actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Valoración no encontrada")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ValoracionResponseDTO>> actualizar(
            @Parameter(description = "ID de la valoración", required = true, example = "1")
            @PathVariable long id,
            @Valid @RequestBody ValoracionRequestDTO dto) {
        ValoracionResponseDTO actualizada = valoracionService.updateValoracion(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @Operation(summary = "Eliminar una valoración", description = "Elimina una valoración del sistema por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Valoración eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Valoración no encontrada")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la valoración a eliminar", required = true, example = "1")
            @PathVariable long id) {
        valoracionService.deleteValoracion(id);
        return ResponseEntity.noContent().build();
    }
}