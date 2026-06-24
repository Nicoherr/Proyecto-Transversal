package com.marketplace.inventario.controller;

import com.marketplace.inventario.assemblers.InventarioModelAssembler;
import com.marketplace.inventario.dto.InventarioRequestDTO;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Inventario", description = "Operaciones de inventario con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService service;
    private final InventarioModelAssembler assembler;

    public InventarioController(InventarioService service, InventarioModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los inventarios con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<InventarioResponseDTO>> listar() {
        log.info("GET /api/v2/inventarios - Listar con HATEOAS");

        List<EntityModel<InventarioResponseDTO>> inventarios = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener inventario por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<InventarioResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/v2/inventarios/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.obtener(id));
    }

    @Operation(summary = "Crear inventario con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<InventarioResponseDTO>> crear(@Valid @RequestBody InventarioRequestDTO dto) {
        log.info("POST /api/v2/inventarios - Crear con HATEOAS");
        EntityModel<InventarioResponseDTO> model = assembler.toModel(service.crear(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
}

