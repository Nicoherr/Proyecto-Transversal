package com.marketplace.producto.controller;

import com.marketplace.producto.dto.ProductoRequestDTO;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@RequestBody ProductoRequestDTO dto) {
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }
}

