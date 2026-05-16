package com.marketplace.inventario.controller;

import com.marketplace.inventario.dto.InventarioRequestDTO;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.service.InventarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Slf4j // Habilita los logs
@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> crear(@RequestBody @Valid InventarioRequestDTO dto) {
        // @Valid activa las validaciones del DTO antes de entrar al service
        log.info("POST /api/inventarios - Solicitud para crear inventario para producto ID: {}", dto.getProductoId());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listar() {
        log.info("GET /api/inventarios - Solicitud para listar todos los inventarios");
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/inventarios/{} - Solicitud para obtener inventario", id);
        return ResponseEntity.ok(service.obtener(id));
    }
}