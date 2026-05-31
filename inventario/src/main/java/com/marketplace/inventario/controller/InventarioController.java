package com.marketplace.inventario.controller;

import com.marketplace.inventario.dto.InventarioRequestDTO;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.service.InventarioService;
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

@Tag(name = "Inventario", description = "Operaciones relacionadas con el inventario")
@Slf4j
@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @Operation(summary = "Crear un nuevo inventario", description = "Registra el inventario de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<InventarioResponseDTO> crear(@RequestBody @Valid InventarioRequestDTO dto) {
        log.info("POST /api/inventarios - Solicitud para crear inventario para producto ID: {}", dto.getProductoId());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los inventarios", description = "Obtiene todos los inventarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada con éxito")
    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listar() {
        log.info("GET /api/inventarios - Solicitud para listar todos los inventarios");
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener inventario por ID", description = "Retorna un inventario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "400", description = "Inventario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/inventarios/{} - Solicitud para obtener inventario", id);
        return ResponseEntity.ok(service.obtener(id));
    }
}