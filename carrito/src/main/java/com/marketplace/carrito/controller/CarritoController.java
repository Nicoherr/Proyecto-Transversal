package com.marketplace.carrito.controller;
import com.marketplace.carrito.assemblers.CarritoModelAssembler;
import com.marketplace.carrito.dto.*;
import com.marketplace.carrito.service.CarritoService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// Versión 2 del controller — igual al original pero con HATEOAS
// Se accede por /api/v2/carritos en vez de /api/carritos
@Tag(name = "Carrito V2", description = "Operaciones de carrito con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService service;
    private final CarritoModelAssembler assembler;

    public CarritoController(CarritoService service, CarritoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // Retorna un EntityModel con el carrito + sus links HATEOAS
    @Operation(summary = "Crear carrito con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<CarritoResponseDTO>> crear(@Valid @RequestBody CarritoRequestDTO dto) {
        log.info("POST /api/v2/carritos - Crear carrito con HATEOAS");
        EntityModel<CarritoResponseDTO> model = assembler.toModel(service.crear(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    // Retorna un solo carrito envuelto en EntityModel con sus links
    @Operation(summary = "Obtener carrito por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<CarritoResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/v2/carritos/{} - Obtener carrito con HATEOAS", id);
        return assembler.toModel(service.obtener(id));
    }

    // Retorna lista de productos del carrito con link self a la colección
    @Operation(summary = "Listar productos del carrito con HATEOAS")
    @GetMapping(value = "/{carritoId}/productos", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CarritoProductoResponseDTO> listarProductos(@PathVariable Long carritoId) {
        log.info("GET /api/v2/carritos/{}/productos - Listar productos con HATEOAS", carritoId);

        List<CarritoProductoResponseDTO> productos = service.listarProductos(carritoId);

        // CollectionModel envuelve la lista y le agrega un link self a la colección
        return CollectionModel.of(productos,
                linkTo(methodOn(CarritoController.class).listarProductos(carritoId)).withSelfRel());
    }

    // Agrega producto con HATEOAS — retorna el producto creado
    @Operation(summary = "Agregar producto al carrito con HATEOAS")
    @PostMapping(value = "/productos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarritoProductoResponseDTO> agregarProducto(@RequestBody CarritoProductoRequestDTO dto) {
        log.info("POST /api/v2/carritos/productos - Agregar producto con HATEOAS");
        CarritoProductoResponseDTO resultado = service.agregarProducto(dto);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
}

