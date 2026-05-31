package com.marketplace.carrito.controller;
import com.marketplace.carrito.dto.*;
import com.marketplace.carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Tag agrupa todos los endpoints bajo "Carrito" en la UI de Swagger
@Tag(name = "Carrito", description = "Operaciones relacionadas con el carrito de compras")
@Slf4j
@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @Operation(summary = "Crear un nuevo carrito", description = "Crea un carrito vacío para un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<CarritoResponseDTO> crear(@Valid @RequestBody CarritoRequestDTO dto) {
        log.info("POST /api/carritos - Solicitud para crear carrito para usuario ID: {}", dto.getUsuarioId());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener carrito por ID", description = "Retorna un carrito específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "400", description = "Carrito no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/carritos/{} - Solicitud para obtener carrito", id);
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Agregar producto al carrito", description = "Agrega un producto con su cantidad a un carrito existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/productos")
    public ResponseEntity<CarritoProductoResponseDTO> agregarProducto(@RequestBody CarritoProductoRequestDTO dto) {
        log.info("POST /api/carritos/productos - Agregar producto ID: {} al carrito ID: {}", dto.getProductoId(), dto.getCarritoId());
        return new ResponseEntity<>(service.agregarProducto(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar productos del carrito", description = "Retorna todos los productos de un carrito específico")
    @ApiResponse(responseCode = "200", description = "Lista retornada con éxito")
    @GetMapping("/{carritoId}/productos")
    public ResponseEntity<List<CarritoProductoResponseDTO>> listarProductos(@PathVariable Long carritoId) {
        log.info("GET /api/carritos/{}/productos - Listar productos del carrito", carritoId);
        return ResponseEntity.ok(service.listarProductos(carritoId));
    }
}