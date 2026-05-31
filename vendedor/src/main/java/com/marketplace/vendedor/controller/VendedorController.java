package com.marketplace.vendedor.controller;
import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.service.VendedorService;
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

@Tag(name = "Vendedores", description = "Operaciones relacionadas con los vendedores")
@Slf4j
@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    private final VendedorService service;

    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @Operation(summary = "Crear un nuevo vendedor", description = "Registra una nueva tienda vendedora")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vendedor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<VendedorResponseDTO> crear(@RequestBody @Valid VendedorRequestDTO dto) {
        log.info("POST /api/vendedores - Solicitud para crear vendedor: {}", dto.getNombreTienda());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los vendedores", description = "Obtiene una lista de todos los vendedores")
    @ApiResponse(responseCode = "200", description = "Lista retornada con éxito")
    @GetMapping
    public ResponseEntity<List<VendedorResponseDTO>> listar() {
        log.info("GET /api/vendedores - Solicitud para listar todos los vendedores");
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener vendedor por ID", description = "Retorna un vendedor específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor encontrado"),
            @ApiResponse(responseCode = "400", description = "Vendedor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VendedorResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/vendedores/{} - Solicitud para obtener vendedor", id);
        return ResponseEntity.ok(service.obtener(id));
    }
}