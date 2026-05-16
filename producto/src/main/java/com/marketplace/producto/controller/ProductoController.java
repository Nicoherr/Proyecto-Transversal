package com.marketplace.producto.controller;

import com.marketplace.producto.dto.ProductoRequestDTO;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.service.ProductoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Habilita los logs
@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@RequestBody @Valid ProductoRequestDTO dto) {
        // @Valid activa las validaciones del DTO antes de entrar al service
        log.info("POST /api/producto - Solicitud para crear producto: {}", dto.getNombre());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        log.info("GET /api/producto - Solicitud para listar todos los productos");
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/producto/{} - Solicitud para obtener producto", id);
        return ResponseEntity.ok(service.obtener(id));
    }
}
