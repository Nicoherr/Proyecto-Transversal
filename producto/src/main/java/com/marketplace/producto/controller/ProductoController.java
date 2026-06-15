package com.marketplace.producto.controller;
import com.marketplace.producto.assemblers.ProductoModelAssembler;
import com.marketplace.producto.dto.ProductoRequestDTO;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.service.ProductoService;
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

@Tag(name = "Productos V2", description = "Operaciones de producto con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService service;
    private final ProductoModelAssembler assembler;

    public ProductoController(ProductoService service, ProductoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los productos con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<ProductoResponseDTO>> listar() {
        log.info("GET /api/v2/producto - Listar con HATEOAS");

        List<EntityModel<ProductoResponseDTO>> productos = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener producto por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<ProductoResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/v2/producto/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.obtener(id));
    }

    @Operation(summary = "Crear producto con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoResponseDTO>> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        log.info("POST /api/v2/producto - Crear con HATEOAS");
        EntityModel<ProductoResponseDTO> model = assembler.toModel(service.crear(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
}

