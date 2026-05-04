package com.marketplace.inventario.controller;

import com.marketplace.inventario.dto.InventarioRequestDTO;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.service.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> crear(@RequestBody InventarioRequestDTO dto) {
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }
}