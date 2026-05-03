package com.marketplace.vendedor.controller;
import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.service.VendedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    private final VendedorService service;

    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VendedorResponseDTO> crear(@RequestBody VendedorRequestDTO dto) {
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VendedorResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }
}

