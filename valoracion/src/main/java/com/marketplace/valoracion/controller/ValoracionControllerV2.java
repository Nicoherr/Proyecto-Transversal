package com.marketplace.valoracion.controller;

import com.marketplace.valoracion.assemblers.ValoracionModelAssembler;
import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.service.ValoracionService;
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

@Tag(name = "Valoraciones V2", description = "Operaciones de valoración con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/v2/valoracion")
public class ValoracionControllerV2 {

    private final ValoracionService service;
    private final ValoracionModelAssembler assembler;

    public ValoracionControllerV2(ValoracionService service, ValoracionModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todas las valoraciones con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<ValoracionResponseDTO>> listar() {
        log.info("GET /api/v2/valoracion - Listar con HATEOAS");
        List<EntityModel<ValoracionResponseDTO>> valoraciones = service.findAllValoraciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(valoraciones,
                linkTo(methodOn(ValoracionControllerV2.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener valoración por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<ValoracionResponseDTO> obtener(
            @Parameter(description = "ID de la valoración", required = true, example = "1")
            @PathVariable long id) {
        log.info("GET /api/v2/valoracion/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.findValoracionById(id));
    }

    @Operation(summary = "Crear valoración con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ValoracionResponseDTO>> crear(@Valid @RequestBody ValoracionRequestDTO dto) {
        log.info("POST /api/v2/valoracion - Crear con HATEOAS");
        EntityModel<ValoracionResponseDTO> model = assembler.toModel(service.makeValoracion(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar valoración con HATEOAS")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ValoracionResponseDTO>> actualizar(
            @Parameter(description = "ID de la valoración", required = true, example = "1")
            @PathVariable long id,
            @Valid @RequestBody ValoracionRequestDTO dto) {
        log.info("PUT /api/v2/valoracion/{} - Actualizar con HATEOAS", id);
        EntityModel<ValoracionResponseDTO> model = assembler.toModel(service.updateValoracion(id, dto));
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar valoración")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la valoración", required = true, example = "1")
            @PathVariable long id) {
        log.info("DELETE /api/v2/valoracion/{} - Eliminar", id);
        service.deleteValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
