package com.marketplace.producto.controller;
import com.marketplace.producto.dto.ProductoRequestDTO;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.service.ProductoService;
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

@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
@Slf4j
@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Crear un nuevo producto", description = "Registra un nuevo producto en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@RequestBody @Valid ProductoRequestDTO dto) {
        log.info("POST /api/producto - Solicitud para crear producto: {}", dto.getNombre());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los productos", description = "Obtiene todos los productos del catálogo")
    @ApiResponse(responseCode = "200", description = "Lista retornada con éxito")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        log.info("GET /api/producto - Solicitud para listar todos los productos");
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "400", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/producto/{} - Solicitud para obtener producto", id);
        return ResponseEntity.ok(service.obtener(id));
    }
}