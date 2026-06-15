package com.marketplace.vendedor.controller;
import com.marketplace.vendedor.assemblers.VendedorModelAssembler;
import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.service.VendedorService;
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

@Tag(name = "Vendedores V2", description = "Operaciones de vendedor con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    private final VendedorService service;
    private final VendedorModelAssembler assembler;

    public VendedorController(VendedorService service, VendedorModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los vendedores con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<VendedorResponseDTO>> listar() {
        log.info("GET /api/v2/vendedores - Listar con HATEOAS");

        List<EntityModel<VendedorResponseDTO>> vendedores = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(vendedores,
                linkTo(methodOn(VendedorController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener vendedor por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<VendedorResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/v2/vendedores/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.obtener(id));
    }

    @Operation(summary = "Crear vendedor con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<VendedorResponseDTO>> crear(@Valid @RequestBody VendedorRequestDTO dto) {
        log.info("POST /api/v2/vendedores - Crear con HATEOAS");
        EntityModel<VendedorResponseDTO> model = assembler.toModel(service.crear(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
}

